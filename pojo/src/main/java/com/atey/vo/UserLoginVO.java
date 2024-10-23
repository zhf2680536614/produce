package com.atey.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@ApiModel(value="登录成功User对象", description="登录成功返回用户")
public class UserLoginVO {

    @ApiModelProperty("用户id")
    private Integer id;

    @ApiModelProperty("jwt令牌")
    private String token;
}
