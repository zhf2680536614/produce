package com.atey.entity;

import com.baomidou.mybatisplus.annotation.TableField;
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
 * 市场秒杀产品
 * </p>
 *
 * @author atey
 * @since 2024-11-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("market_produces_plus")
@ApiModel(value="MarketProducesPlus对象", description="市场秒杀产品")
public class MarketProducesPlus implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "商品名称")
    @TableField("name")
    private String name;

    @ApiModelProperty(value = "分类id")
    @TableField("category")
    private Integer category;

    @ApiModelProperty(value = "产地")
    @TableField("origin")
    private String origin;

    @ApiModelProperty(value = "商品图片")
    @TableField("image")
    private String image;

    @ApiModelProperty(value = "商品重量（g）")
    @TableField("weight")
    private Double weight;

    @ApiModelProperty(value = "商品单价")
    @TableField("unit_price")
    private Double unitPrice;

    @ApiModelProperty(value = "产品介绍")
    @TableField("description")
    private String description;

    @ApiModelProperty(value = "上架用户")
    @TableField("user_id")
    private Integer userId;

    @ApiModelProperty(value = "逻辑删除 1为未删除，2为已删除")
    @TableField("deleted")
    private Integer deleted;

    @ApiModelProperty(value = "状态 1为上架，2为下架")
    @TableField("status")
    private Long status;

    @ApiModelProperty(value = "创建时间")
    @TableField("create_time")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "修改时间")
    @TableField("update_time")
    private LocalDateTime updateTime;


}
