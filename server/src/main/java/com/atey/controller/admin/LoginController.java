package com.atey.controller.admin;

import com.atey.constant.JwtClaimsConstant;
import com.atey.dto.UserDTO;
import com.atey.entity.User;
import com.atey.properties.JwtProperties;
import com.atey.result.Result;
import com.atey.service.IUserService;
import com.atey.utils.JwtUtil;
import com.atey.vo.UserLoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController("AdminLogin")
@RequestMapping("/admin")
@Api(tags = "管理员登录相关接口")
@Slf4j
@AllArgsConstructor
public class LoginController {

    private final JwtProperties jwtProperties;
    private final IUserService userService;

    /**
     * 用户登录
     *
     * @param userDTO
     * @return
     */
    @PostMapping("/login")
    @ApiOperation("管理员登录")
    public Result login(@RequestBody UserDTO userDTO) {

        log.info("管理员登录：{}", userDTO);

        User one = userService.adminLogin(userDTO);

        //登录成功后，生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.ADMIN_ID, one.getId());
        String token = JwtUtil.createJWT(
                jwtProperties.getAdminSecretKey(),
                jwtProperties.getAdminTtl(),
                claims);

        UserLoginVO userLoginVO = UserLoginVO.builder()
                .id(one.getId())
                .token(token)
                .build();

        return Result.success(userLoginVO);
    }
}
