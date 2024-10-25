package com.atey.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serial;
import java.time.LocalDateTime;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author atey
 * @since 2024-10-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName
@ApiModel(value="User对象", description="用户表")
public class User implements Serializable {

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

    @ApiModelProperty(value = "逻辑删除 1为未删除，2为已删除")
    @TableField("deleted")
    private Integer deleted;

    @ApiModelProperty(value = "创建时间")
    @TableField("create_time")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "修改时间")
    @TableField("update_time")
    private LocalDateTime updateTime;


}
