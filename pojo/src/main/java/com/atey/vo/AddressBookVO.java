package com.atey.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AddressBookVO {
    private Short number;
    //地址id
    private Integer id;

    @ApiModelProperty(value = "收获地址")
    private String location;

    @ApiModelProperty(value = "收货人姓名")
    private String consigneeName;

    @ApiModelProperty(value = "收货人电话号码")
    private String consigneePhoneNumber;

    @ApiModelProperty(value = "是否为默认 1为默认，2为不是默认")
    private Boolean isDefault;
}
