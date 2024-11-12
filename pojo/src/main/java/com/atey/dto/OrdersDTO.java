package com.atey.dto;

import lombok.Data;

@Data
public class OrdersDTO {
    //用户id
    private Integer userId;

    //用户名
    private String username;

    //商家id
    private Integer merchantId;

    //商家名称
    private String merchantName;
}
