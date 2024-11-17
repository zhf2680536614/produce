package com.atey.controller.user;

import com.atey.dto.UserDTO;
import com.atey.result.Result;
import com.atey.service.IUserService;
import com.atey.vo.UserVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@Api(tags="用户注册相关接口")
@Slf4j
@AllArgsConstructor
public class RegisterController {

    private final IUserService userService;

    /**
     * 用户注册
     * @param userDTO
     * @return
     */
    @PostMapping("/register")
    @ApiOperation("用户注册")
    @CacheEvict(cacheNames = {"userCache", "chartCache"}, allEntries = true)
    public Result register(@RequestBody UserDTO userDTO){
        log.info("用户注册{}",userDTO);
        userService.register(userDTO);
        return Result.success();
    }

    /**
     * 用户验证
     * @param userDTO
     * @return
     */
    @PostMapping("/validate")
    @ApiOperation("用户验证")
    public Result validate(@RequestBody UserDTO userDTO){
        log.info("用户验证{}",userDTO);
        UserVO userVO = userService.validate(userDTO);
        return Result.success(userVO);
    }

    @PutMapping("/reset")
    @ApiOperation("重置密码")
    @CacheEvict(cacheNames = {"userCache", "chartCache"}, allEntries = true)
    public Result reset(@RequestBody UserDTO userDTO){
        log.info("重置密码{}",userDTO);
        userService.updateUser(userDTO);
        return Result.success();
    }
}
