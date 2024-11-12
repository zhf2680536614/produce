package com.atey.dto;

import lombok.Data;

@Data
public class OrdersDetailsDTO {
    private String produceName;
    private String produceCategory;
    private Double unitPrice;
    private Integer ordersId;
    private Double weight;
}
