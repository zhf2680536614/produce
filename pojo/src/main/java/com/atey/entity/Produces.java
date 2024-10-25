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
 * 农产品
 * </p>
 *
 * @author atey
 * @since 2024-10-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("produces")
@ApiModel(value="Produces对象", description="农产品")
public class Produces implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "名称")
    @TableField("name")
    private String name;

    @ApiModelProperty(value = "分类id")
    @TableField("category")
    private Integer category;

    @ApiModelProperty(value = "发源地")
    @TableField("origin")
    private String origin;

    @ApiModelProperty(value = "图片")
    @TableField("image")
    private String image;

    @ApiModelProperty(value = "描述")
    @TableField("description")
    private String description;

    @ApiModelProperty(value = "生长环境")
    @TableField("growth_cycle")
    private String growthCycle;

    @ApiModelProperty(value = "生长周期")
    @TableField("growth_time")
    private LocalDateTime growthTime;

    @ApiModelProperty(value = "生长习性")
    @TableField("shelf_life")
    private Integer shelfLife;

    @ApiModelProperty(value = "创建时间")
    @TableField("create_time")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "修改时间")
    @TableField("update_time")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "逻辑删除 1为未删除，2为已删除")
    @TableField("deleted")
    private Integer deleted;

    @ApiModelProperty(value = "状态 1为启动，2为禁用")
    @TableField("status")
    private Integer status;


}
