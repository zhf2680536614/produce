package com.atey.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.atey.constant.*;
import com.atey.dto.PageDTO;
import com.atey.dto.UserDTO;
import com.atey.entity.AddressBook;
import com.atey.query.UserQuery;
import com.atey.entity.User;
import com.atey.exception.BaseException;
import com.atey.mapper.UserMapper;
import com.atey.result.Result;
import com.atey.service.IUserService;
import com.atey.vo.AddressBookVO;
import com.atey.vo.UserAddressVO;
import com.atey.vo.UserVO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author atey
 * @since 2024-10-23
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    private final UserMapper userMapper;

    /**
     * 管理端用户分页查询
     *
     * @param userQuery
     * @return
     */
    @Override
    @CachePut(value = "userCache", key = "#userQuery.toString() + '-' + #userQuery.pageNo.toString()")
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
                .orderByDesc(User::getCreateTime)
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

        if (userDTO.getGender() == null) {
            throw new BaseException(MessageConstant.CHOOSE_GENDER);
        }
        if (userDTO.getType() == null) {
            throw new BaseException(MessageConstant.CHOOSE_TYPE);
        }
        if (userDTO.getStatus() == null) {
            throw new BaseException(MessageConstant.CHOOSE_STATUS);
        }

        //查询数据库中用户名是否存在
        User one = lambdaQuery()
                .eq(userDTO.getUsername() != null, User::getUsername, userDTO.getUsername())
                .eq(User::getDeleted, DeletedConstant.NOT_DELETED)
                .one();

        if (BeanUtil.isEmpty(one)) {
            User user = new User();
            BeanUtil.copyProperties(userDTO, user);
            user.setDeleted(DeletedConstant.NOT_DELETED);
            user.setCreateTime(LocalDateTime.now());
            user.setUpdateTime(LocalDateTime.now());
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
     *
     * @param userDTO
     * @return
     */
    @Override
    public User userLogin(UserDTO userDTO) {

        String username = userDTO.getUsername();
        String password = userDTO.getPassword();

        User one = lambdaQuery()
                .eq(username != null, User::getUsername, userDTO.getUsername())
                .eq(password != null, User::getPassword, userDTO.getPassword())
                .eq(User::getDeleted, DeletedConstant.NOT_DELETED)
                .eq(User::getStatus, StatusConstant.ENABLE)
                .one();

        if (BeanUtil.isEmpty(one)) {
            throw new BaseException("登录失败，账号或密码错误!");
        }

        return one;
    }

    /**
     * 管理员登录
     *
     * @param userDTO
     * @return
     */
    @Override
    public User adminLogin(UserDTO userDTO) {
        String username = userDTO.getUsername();
        String password = userDTO.getPassword();

        User one = lambdaQuery()
                .eq(username != null, User::getUsername, userDTO.getUsername())
                .eq(password != null, User::getPassword, userDTO.getPassword())
                .eq(User::getDeleted, DeletedConstant.NOT_DELETED)
                .eq(User::getType, TypeConstant.ADMIN)
                .eq(User::getStatus, StatusConstant.ENABLE)
                .one();

        if (BeanUtil.isEmpty(one)) {
            throw new BaseException("登录失败，账号或密码错误!");
        }

        return one;
    }

    /**
     * 根据id获取用户信息及收货地址
     *
     * @param id
     * @return
     */
    @Override
    public UserAddressVO getByIdUserWithAddress(Long id) {
        UserVO userVO = getByIdUser(id);
        UserAddressVO userAddressVO = new UserAddressVO();
        BeanUtil.copyProperties(userVO, userAddressVO);

        //获取用户的收货地址
        List<AddressBook> addressList = Db.lambdaQuery(AddressBook.class)
                .eq(AddressBook::getUserId, id)
                .eq(AddressBook::getDeleted, DeletedConstant.NOT_DELETED)
                .list();

        List<AddressBookVO> addressBookVOS = new ArrayList<>();

        short number = 0;

        for (AddressBook addressBook : addressList) {

            AddressBookVO addressBookVO = new AddressBookVO();
            BeanUtil.copyProperties(addressBook, addressBookVO);
            if (Objects.equals(addressBook.getIsDefault(), DefaultConstant.IS_DEFAULT)) {
                addressBookVO.setIsDefault(true);
            } else {
                addressBookVO.setIsDefault(false);
            }
            number++;
            addressBookVO.setNumber(number);
            addressBookVOS.add(addressBookVO);
        }
        userAddressVO.setAddress(addressBookVOS);
        return userAddressVO;
    }

    /**
     * 用户注册
     *
     * @param userDTO
     */
    @Override
    public void register(UserDTO userDTO) {
        //查询数据库中用户名是否存在
        User one = lambdaQuery()
                .eq(userDTO.getUsername() != null, User::getUsername, userDTO.getUsername())
                .eq(User::getDeleted, DeletedConstant.NOT_DELETED)
                .one();

        if (BeanUtil.isEmpty(one)) {
            User user = new User();
            BeanUtil.copyProperties(userDTO, user);
            user.setDeleted(DeletedConstant.NOT_DELETED);
            user.setCreateTime(LocalDateTime.now());
            user.setUpdateTime(LocalDateTime.now());
            user.setStatus(StatusConstant.ENABLE);
            user.setType(TypeConstant.USER);
            //设置默认头像
            user.setImage("https://produces.oss-cn-beijing.aliyuncs.com/2021c9fa-7b3c-4bf8-ac5d-eeb3853f9fa0.png");
            user.setGender((short)1);
            save(user);
        } else {
            throw new BaseException(MessageConstant.USER_EXIST_SAVE);
        }
    }

    /**
     * 用户验证
     * @param userDTO
     * @return
     */
    @Override
    public UserVO validate(UserDTO userDTO) {

        String username = userDTO.getUsername();
        String phoneNumber = userDTO.getPhoneNumber();
        String email = userDTO.getEmail();

        User user = lambdaQuery()
                .eq(username != null, User::getUsername, userDTO.getUsername())
                .eq(email != null, User::getEmail, userDTO.getEmail())
                .eq(phoneNumber != null, User::getPhoneNumber, userDTO.getPhoneNumber())
                .eq(User::getDeleted, DeletedConstant.NOT_DELETED)
                .eq(User::getStatus, StatusConstant.ENABLE)
                .eq(User::getType, TypeConstant.USER)
                .one();

        if(BeanUtil.isEmpty(user)) {
            throw new BaseException("验证失败");
        }

        UserVO userVO = new UserVO();
        BeanUtil.copyProperties(user, userVO);

        return userVO;
    }
}
