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
 * 订单明细表
 * </p>
 *
 * @author atey
 * @since 2024-10-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("orders_detail")
@ApiModel(value="OrdersDetail对象", description="订单明细表")
public class OrdersDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "订单id")
    private Integer ordersId;

    @ApiModelProperty(value = "订单号")
    private String ordersNumber;

    @ApiModelProperty(value = "产品名称")
    private String produceName;

    @ApiModelProperty(value = "产品分类")
    private String produceCategory;

    @ApiModelProperty(value = "产品重量")
    private Double produceWeight;

    @ApiModelProperty(value = "产品单价")
    private Double unitPrice;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "修改时间")
    private LocalDateTime updateTime;


}
