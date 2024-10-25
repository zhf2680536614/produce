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
 * 收货地址
 * </p>
 *
 * @author atey
 * @since 2024-10-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("address_book")
@ApiModel(value="AddressBook对象", description="收货地址")
public class AddressBook implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "收获地址")
    private String location;

    @ApiModelProperty(value = "用户id")
    private Integer userId;

    @ApiModelProperty(value = "收货人姓名")
    private String consigneeName;

    @ApiModelProperty(value = "收货人电话号码")
    private String consigneePhoneNumber;

    @ApiModelProperty(value = "逻辑删除 1为未删除，2为已删除")
    private Integer deleted;

    @ApiModelProperty(value = "是否为默认 1为默认，2为不是默认")
    private Integer isDefault;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "修改时间")
    private LocalDateTime updateTime;


}
