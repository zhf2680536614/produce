package com.atey.entity;

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
 * 市场上架商品
 * </p>
 *
 * @author atey
 * @since 2024-10-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("market_produces")
@ApiModel(value="MarketProduces对象", description="市场上架商品")
public class MarketProduces implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "商品名称")
    private String name;

    @ApiModelProperty(value = "分类id")
    private Integer category;

    @ApiModelProperty(value = "发源地")
    private String origin;

    @ApiModelProperty(value = "产品图片")
    private String image;

    @ApiModelProperty(value = "产品重量（kg）")
    private Double weight;

    @ApiModelProperty(value = "产品单价")
    private Double unitPrice;

    @ApiModelProperty(value = "产品介绍")
    private String description;

    @ApiModelProperty(value = "上架用户id")
    private Integer userId;

    @ApiModelProperty(value = "逻辑删除 1为未删除，2为已删除")
    private Integer deleted;

    @ApiModelProperty(value = "状态 1为上架，2为下架")
    private Integer status;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "修改时间")
    private LocalDateTime updateTime;


}
