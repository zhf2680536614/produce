package com.atey.controller.user;

import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("UserUserController")
@RequestMapping("/admin/user")
@Api(tags="管理端用户相关接口")
@Slf4j
@AllArgsConstructor
public class UserController {

}
