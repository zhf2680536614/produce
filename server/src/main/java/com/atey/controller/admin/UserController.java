package com.atey.controller.admin;

import com.atey.dto.PageDTO;
import com.atey.dto.UserDTO;
import com.atey.query.UserQuery;
import com.atey.result.Result;
import com.atey.service.IUserService;
import com.atey.vo.UserVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

@RestController("AdminUserController")
@RequestMapping("/admin/user")
@Api(tags = "管理端用户相关接口")
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final IUserService userService;

    /**
     * 管理端用户分页查询
     *
     * @param userQuery
     * @return
     */
    @GetMapping("/page")
    @ApiOperation("用户信息分页查询")
    @Cacheable(cacheNames = "userCache", key = "#userQuery.toString() + '-' + #userQuery.pageNo.toString()")
    public Result<PageDTO<UserVO>> pageQuery(UserQuery userQuery) {
        log.info("管理端用户分页查询 {} {} {}", userQuery.getPageNo(), userQuery.getPageSize(), userQuery);
        return userService.pageQuery(userQuery);
    }

    /**
     * 新增用户
     *
     * @param userDTO
     * @return
     */
    @PostMapping("/save")
    @ApiOperation("添加用户")
    @CacheEvict(cacheNames = {"userCache", "chartCache"}, allEntries = true)
    public Result save(@RequestBody UserDTO userDTO) {
        log.info("新增用户{}", userDTO.toString());
        userService.save(userDTO);
        return Result.success();
    }

    /**
     * 删除用户
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    @ApiOperation("删除用户")
    @CacheEvict(cacheNames = {"userCache", "chartCache"}, allEntries = true)
    public Result delete(@PathVariable Long id) {
        log.info("删除用户{}", id);
        userService.delete(id);
        return Result.success();
    }

    /**
     * 根据id查询用户
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @ApiOperation("根据id查询用户")
    public Result<UserVO> getById(@PathVariable Long id) {
        log.info("根据id查询用户{}", id);
        UserVO userVO = userService.getByIdUser(id);
        return Result.success(userVO);
    }

    /**
     * 修改用户
     *
     * @param userDTO
     * @return
     */
    @PutMapping("/update")
    @ApiOperation("修改用户")
    @CacheEvict(cacheNames = {"userCache", "chartCache"}, allEntries = true)
    public Result update(@RequestBody UserDTO userDTO) {
        log.info("用户管理修改用户{}", userDTO);
        userService.updateUser(userDTO);
        return Result.success();
    }
}
