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
 * 农产品——分类表
 * </p>
 *
 * @author atey
 * @since 2024-10-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("produces_category")
@ApiModel(value="ProducesCategory对象", description="农产品——分类表")
public class ProducesCategory implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "农产品id")
    @TableField("produce_id")
    private Integer produceId;

    @ApiModelProperty(value = "分类id")
    @TableField("category_id")
    private Integer categoryId;

    @ApiModelProperty(value = "创建时间")
    @TableField("create_time")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "修改日期")
    @TableField("update_time")
    private LocalDateTime updateTime;


}
