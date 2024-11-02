package com.atey.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MarketProducesVO {

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "商品名称")
    private String name;

    @ApiModelProperty(value = "分类名称")
    private String category;

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

    @ApiModelProperty(value = "上架用户姓名")
    private String username;

    @ApiModelProperty(value = "状态 1为上架，2为下架")
    private Long status;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "修改时间")
    private LocalDateTime updateTime;
}
