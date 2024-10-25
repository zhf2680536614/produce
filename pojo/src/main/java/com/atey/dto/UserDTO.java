package com.atey.dto;

import com.atey.entity.User;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class UserDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "用户名")
    @TableField("username")
    private String username;

    @ApiModelProperty(value = "密码")
    @TableField("password")
    private String password;

    @ApiModelProperty(value = "用户权限 1为普通用户，2为管理员")
    @TableField("type")
    private Integer type;

    @ApiModelProperty(value = "账号状态 1为启用，2为禁用")
    @TableField("status")
    private Integer status;

    @ApiModelProperty(value = "账号头像")
    @TableField("image")
    private String image;

    @ApiModelProperty(value = "性别")
    @TableField("gender")
    private Short gender;

    @ApiModelProperty(value = "电话号码")
    @TableField("phone_number")
    private String phoneNumber;

    @ApiModelProperty(value = "邮箱")
    @TableField("email")
    private String email;
}
