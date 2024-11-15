package com.atey.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserOrdersVO {

    @ApiModelProperty(value = "订单号")
    private Integer id;

    @ApiModelProperty(value="订单明细id")
    private Integer ordersDetailsId;

    @ApiModelProperty(value = "订单号")
    private String orderNumber;

    @ApiModelProperty(value = "用户id")
    private Integer userId;

    @ApiModelProperty(value = "用户名称")
    private String username;

    @ApiModelProperty(value="上架产品id")
    private Integer marketProducesId;

    @ApiModelProperty(value = "商家名称")
    private String merchantName;

    @ApiModelProperty(value = "收货人姓名")
    private String consigneeName;

    @ApiModelProperty(value = "收货地址名称")
    private String addressBookName;

    @ApiModelProperty(value = "用户手机号码")
    private String phoneNumber;

    @ApiModelProperty(value = "订单状态 1为已完成，2为待确认，3为已取消")
    private Integer status;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "产品名称")
    private String produceName;

    @ApiModelProperty(value = "产品图片")
    private String image;

    @ApiModelProperty(value = "产品分类")
    private String produceCategory;

    @ApiModelProperty(value = "产品重量")
    private Double produceWeight;

    @ApiModelProperty(value = "产品单价")
    private Double unitPrice;

    @ApiModelProperty(value = "订单总金额")
    private Double amount;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "修改时间")
    private LocalDateTime updateTime;
}
