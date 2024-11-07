package com.atey.dto;


import lombok.Data;

@Data
public class UpdateOrdersDTO {
    private Integer id;
    private Integer addressBookId;
    private String consigneeName;
    private String consigneePhoneNumber;
    private String addressBookName;
    private String remark;
    private Integer marketProducesId;
}
