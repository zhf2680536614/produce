package com.atey.vo;

import lombok.Data;

import java.util.List;

@Data
public class ChartCategoryVO {
    //分类名称集合
    private List<String> category;
    //分类对应的销售额
    private List<Integer> amount;
}
