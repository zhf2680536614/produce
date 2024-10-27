package com.atey.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.atey.constant.DeletedConstant;
import com.atey.constant.MessageConstant;
import com.atey.constant.StatusConstant;
import com.atey.constant.TypeConstant;
import com.atey.dto.PageDTO;
import com.atey.dto.UserDTO;
import com.atey.query.UserQuery;
import com.atey.entity.User;
import com.atey.exception.BaseException;
import com.atey.mapper.UserMapper;
import com.atey.result.Result;
import com.atey.service.IUserService;
import com.atey.vo.UserVO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author atey
 * @since 2024-10-23
 */
@Service
@AllArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    private final UserMapper userMapper;
    /**
     * 管理端用户分页查询
     *
     * @param userQuery
     * @return
     */
    @Override
    @CachePut(value="userCache",key="#userQuery.pageNo")
    public Result<PageDTO<UserVO>> pageQuery(UserQuery userQuery) {

        String username = userQuery.getUsername();
        String phoneNumber = userQuery.getPhoneNumber();
        LocalDateTime startCreateTime = userQuery.getStartCreateTime();
        LocalDateTime endCreateTime = userQuery.getEndCreateTime();

        Page<User> page = userQuery.toMpPage();

        Page<User> result = lambdaQuery()
                .like(username != null, User::getUsername, username)
                .eq(phoneNumber != null, User::getPhoneNumber, phoneNumber)
                .eq(User::getDeleted, DeletedConstant.NOT_DELETED)
                .between(startCreateTime != null && endCreateTime != null, User::getCreateTime, startCreateTime, endCreateTime)
                .orderByDesc(User::getUpdateTime)
                .page(page);

        return Result.success(PageDTO.of(result, UserVO.class));
    }

    /**
     * 新增用户
     *
     * @param userDTO
     * @return
     */
    @Override
    public void save(UserDTO userDTO) {

        if(userDTO.getGender() == null){
            throw new BaseException(MessageConstant.CHOOSE_GENDER);
        }
        if(userDTO.getType() == null){
            throw new BaseException(MessageConstant.CHOOSE_TYPE);
        }
        if(userDTO.getStatus() == null){
            throw new BaseException(MessageConstant.CHOOSE_STATUS);
        }
        User user = new User();
        BeanUtil.copyProperties(userDTO, user);
        user.setDeleted(DeletedConstant.NOT_DELETED);
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());

        // TODO 图片设置
        user.setImage("https://th.bing.com/th/id/R.e636e5f9ae0388421d048d93ecfbc5b8?rik=XWWt54hFT3Pknw&pid=ImgRaw&r=0");

        //查询数据库中用户名是否存在
        User one = lambdaQuery()
                .eq(userDTO.getUsername() != null, User::getUsername, userDTO.getUsername())
                .eq(User::getDeleted, DeletedConstant.NOT_DELETED)
                .one();
        if (BeanUtil.isEmpty(one)) {
            save(user);
        } else {
            throw new BaseException(MessageConstant.USER_EXIST_SAVE);
        }
    }

    /**
     * 删除用户
     *
     * @param id
     * @return
     */
    @Override
    public void delete(Long id) {

        User user = lambdaQuery()
                .eq(User::getId, id)
                .one();
        user.setDeleted(DeletedConstant.DELETED);

        userMapper.update(user);
    }

    /**
     * 根据id查询用户
     *
     * @param id
     * @return
     */
    @Override
    public UserVO getByIdUser(Long id) {
        User user = getById(id);
        UserVO userVO = new UserVO();
        BeanUtil.copyProperties(user, userVO);
        return userVO;
    }

    /**
     * 修改用户
     *
     * @param userDTO
     * @return
     */
    @Override
    @Transactional
    public void updateUser(UserDTO userDTO) {
        User user = new User();
        BeanUtil.copyProperties(userDTO, user);

        user.setUpdateTime(LocalDateTime.now());

        userMapper.update(user);

        //查询数据库中用户名是否重复
        List<User> list = lambdaQuery()
                .eq(userDTO.getUsername() != null, User::getUsername, userDTO.getUsername())
                .eq(User::getDeleted, DeletedConstant.NOT_DELETED)
                .list();

        if (list.size() >= 2) {
            throw new BaseException(MessageConstant.USER_EXIST_UPDATE);
        }
    }

    /**
     * 用户登录
     * @param userDTO
     * @return
     */
    @Override
    public User userLogin(UserDTO userDTO) {

        String username = userDTO.getUsername();
        String password = userDTO.getPassword();

        User one = lambdaQuery()
                .eq(username!=null, User::getUsername, userDTO.getUsername())
                .eq(password!=null, User::getPassword, userDTO.getPassword())
                .eq(User::getDeleted, DeletedConstant.NOT_DELETED)
                .eq(User::getStatus, StatusConstant.ENABLE)
                .one();

        if(BeanUtil.isEmpty(one)) {
            throw new BaseException("登录失败，账号或密码错误!");
        }

        return one;
    }

    /**
     * 管理员登录
     * @param userDTO
     * @return
     */
    @Override
    public User adminLogin(UserDTO userDTO) {
        String username = userDTO.getUsername();
        String password = userDTO.getPassword();

        User one = lambdaQuery()
                .eq(username!=null, User::getUsername, userDTO.getUsername())
                .eq(password!=null, User::getPassword, userDTO.getPassword())
                .eq(User::getDeleted, DeletedConstant.NOT_DELETED)
                .eq(User::getType, TypeConstant.ADMIN)
                .eq(User::getStatus, StatusConstant.ENABLE)
                .one();

        if(BeanUtil.isEmpty(one)) {
            throw new BaseException("登录失败，账号或密码错误!");
        }

        return one;
    }
}
