package com.atey.vo;

import com.atey.entity.ChartAddUser;
import com.atey.entity.ChartAmount;
import com.atey.entity.ChartBG;
import com.atey.entity.ChartOrders;
import lombok.Data;

import java.util.List;
@Data
public class AdminChartVO {

    //用户总数
    private Long userAmount;

    //分类总数
    private Long categoryAmount;

    //产品总数
    private Long produceMount;

    //男女数
    private List<ChartBG> chartBGList;

    //新增用户数
    private ChartAddUser chartAddUser;

    //订单数
    private List<ChartOrders> ordersList;

    //销售额
    private ChartAmount chartAmount;
}
