package com.atey.controller.user;

import com.atey.dto.UserDTO;
import com.atey.result.Result;
import com.atey.service.IUserService;
import com.atey.vo.UserAddressVO;
import com.atey.vo.UserVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController("UserUserController")
@RequestMapping("/user/user")
@Api(tags = "用户端用户相关接口")
@Slf4j
@AllArgsConstructor
public class UserController {
    private final IUserService userService;

    @GetMapping("/{id}")
    @ApiOperation("用户端根据id查询用户")
    public Result<UserAddressVO> getById(@PathVariable Long id) {
        log.info("根据id查询用户{}", id);
        UserAddressVO userAddressVO = userService.getByIdUserWithAddress(id);
        return Result.success(userAddressVO);
    }

    @PutMapping("/update")
    @ApiOperation("用户端修改用户")
    public Result update(@RequestBody UserDTO userDTO) {
        log.info("用户端修改用户{}", userDTO);
        userService.updateUser(userDTO);
        return Result.success();
    }
}
