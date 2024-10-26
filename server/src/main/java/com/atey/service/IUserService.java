package com.atey.service;

import com.atey.dto.PageDTO;
import com.atey.dto.UserDTO;
import com.atey.query.UserQuery;
import com.atey.entity.User;
import com.atey.vo.UserVO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author atey
 * @since 2024-10-23
 */
public interface IUserService extends IService<User> {
    /**
     * 管理端用户分页查询
     * @param userQuery
     * @return
     */
    PageDTO<UserVO> pageQuery(UserQuery userQuery);

    /**
     * 新增用户
     * @param userDTO
     * @return
     */
    void save(UserDTO userDTO);

    /**
     * 删除用户
     * @param id
     * @return
     */
    void delete(Long id);

    /**
     * 根据id查询用户
     * @param id
     * @return
     */
    UserVO getByIdUser(Long id);

    /**
     * 修改用户
     * @param userDTO
     * @return
     */
    void updateUser(UserDTO userDTO);

    /**
     * 用户登录
     * @param user
     * @return
     */
    User userLogin(UserDTO userDTO);

    /**
     * 管理员登录
     * @param userDTO
     * @return
     */
    User adminLogin(UserDTO userDTO);
}
