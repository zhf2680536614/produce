package com.atey.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 订单表
 * </p>
 *
 * @author atey
 * @since 2024-10-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("orders")
@ApiModel(value="Orders对象", description="订单表")
public class Orders implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "订单号")
    private String orderNumber;

    @ApiModelProperty(value = "用户id")
    private Integer userId;

    @ApiModelProperty(value = "用户名称")
    private String username;

    @ApiModelProperty(value = "商家名称")
    private String merchantName;

    @ApiModelProperty(value = "收货地址id")
    private Integer addressBookId;

    @ApiModelProperty(value = "收货地址名称")
    private String addressBookName;

    @ApiModelProperty(value = "用户手机号码")
    private String phoneNumber;

    @ApiModelProperty(value = "订单状态 1为已完成，2为待确认，3为已取消")
    private Integer status;

    @ApiModelProperty(value = "订单完成时间")
    private LocalDateTime completeTime;

    @ApiModelProperty(value = "订单取消时间")
    private LocalDateTime cancelTime;

    @ApiModelProperty(value = "订单取消原因")
    private String cancelReason;

    @ApiModelProperty(value = "订单总金额")
    private Double amount;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "逻辑删除 1为未删除，2为已删除")
    private Integer deleted;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "修改时间")
    private LocalDateTime updateTime;


}
