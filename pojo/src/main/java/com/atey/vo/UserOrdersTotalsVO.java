package com.atey.vo;

import lombok.Data;

import java.util.List;

@Data
public class UserOrdersTotalsVO {

    private Integer total;

    private List<UserOrdersVO> userOrdersTotals;
}
