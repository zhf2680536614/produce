package com.atey.service.impl;

import cn.hutool.core.util.StrUtil;
import com.atey.constant.DeletedConstant;
import com.atey.constant.StatusConstant;
import com.atey.entity.*;
import com.atey.mapper.OrdersDetailMapper;
import com.atey.mapper.OrdersMapper;
import com.atey.mapper.UserMapper;
import com.atey.service.ChartService;
import com.atey.vo.AdminChartVO;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChartServiceImpl implements ChartService {

    private final UserMapper userMapper;

    private final OrdersMapper ordersMapper;

    private final OrdersDetailMapper ordersDetailMapper;

    /**
     * 管理端仪表盘数据统计
     *
     * @return
     */
    @Override

    public AdminChartVO chart(LocalDate start, LocalDate end) {

        LocalDateTime startDateTime = LocalDateTime.of(start, LocalTime.MIN);
        LocalDateTime endDateTime = LocalDateTime.of(end, LocalTime.MAX);

        //解析时间
        if (start.equals(LocalDate.of(1970, 1, 1))) {
            //查询第一个用户创建的时间
            List<User> list = Db.lambdaQuery(User.class)
                    .orderByAsc(User::getId)
                    .list();
            User user = list.get(0);
            startDateTime = user.getCreateTime();
            System.out.println(startDateTime);
            start = startDateTime.toLocalDate();
        }

        AdminChartVO adminChartVO = new AdminChartVO();

        //统计用户数量
        Long userAmount = Db.lambdaQuery(User.class)
                .eq(User::getDeleted, DeletedConstant.NOT_DELETED)
                .count();
        adminChartVO.setUserAmount(userAmount);

        //统计男女数量
        List<ChartBG> chartBGList = new ArrayList<>();
        ChartBG chartBG1 = ChartBG.builder()
                .gender(1)
                .count(0)
                .build();
        ChartBG chartBG2 = ChartBG.builder()
                .gender(2)
                .count(0)
                .build();
        chartBGList.add(chartBG1);
        chartBGList.add(chartBG2);

        List<ChartBG> temp1;
        temp1 = userMapper.countBG();
        if (temp1 != null) {
            for (ChartBG chartBG : temp1) {
                for (ChartBG item : chartBGList) {
                    if (Objects.equals(chartBG.getGender(), item.getGender())) {
                        item.setCount(chartBG.getCount());
                    }
                }
            }
        }


        adminChartVO.setChartBGList(chartBGList);

        //统计分类总数
        Long categoryAmount = Db.lambdaQuery(Category.class)
                .eq(Category::getDeleted, DeletedConstant.NOT_DELETED)
                .count();
        adminChartVO.setCategoryAmount(categoryAmount);

        //统计产品总数
        Long produceAmount = Db.lambdaQuery(Produces.class)
                .eq(Produces::getDeleted, DeletedConstant.NOT_DELETED)
                .count();
        adminChartVO.setProduceAmount(produceAmount);

        //统计订单数
        List<ChartOrders> chartOrdersList = new ArrayList<>();

        ChartOrders chartOrders1 = ChartOrders.builder()
                .status(1)
                .count(0)
                .build();
        ChartOrders chartOrders2 = ChartOrders.builder()
                .status(2)
                .count(0)
                .build();
        ChartOrders chartOrders3 = ChartOrders.builder()
                .status(3)
                .count(0)
                .build();

        chartOrdersList.add(chartOrders1);
        chartOrdersList.add(chartOrders2);
        chartOrdersList.add(chartOrders3);

        List<ChartOrders> temp2;
        temp2 = ordersMapper.countOrders(startDateTime, endDateTime);
        if (temp2 != null) {
            for (ChartOrders chartOrders : temp2) {
                for (ChartOrders item : chartOrdersList) {
                    if (Objects.equals(chartOrders.getStatus(), item.getStatus())) {
                        item.setCount(chartOrders.getCount());
                    }
                }
            }
        }

        int amount = 0;
        for (ChartOrders item : chartOrdersList) {
            amount += item.getCount();
        }
        ChartOrders chartOrders = ChartOrders.builder()
                .status(0)
                .count(amount)
                .build();
        chartOrdersList.add(chartOrders);
        adminChartVO.setOrdersList(chartOrdersList);

        //创建集合存放范围日期
        List<LocalDate> dateList = new ArrayList<>();
        dateList.add(start);
        while (!start.equals(end)) {
            start = start.plusDays(1);
            dateList.add(start);
        }
        ChartAddUser chartAddUser = new ChartAddUser();
        chartAddUser.setDateList(dateList);
        ChartAmount chartAmount = new ChartAmount();
        chartAmount.setDataList(dateList);
        //统计新增用户数
        //统计销售额
        List<Integer> addUserList = new ArrayList<>();
        List<Integer> amountList = new ArrayList<>();
        for (LocalDate item : dateList) {
            LocalDateTime startTime = LocalDateTime.of(item, LocalTime.MIN);
            LocalDateTime endTime = LocalDateTime.of(item, LocalTime.MAX);
            Map<String, LocalDateTime> map = new HashMap<>();
            map.put("end", endTime);
            map.put("begin", startTime);
            Integer newUser = userMapper.countByMap(map);
            addUserList.add(newUser);

            Double ordersAmount = ordersDetailMapper.countByMap(map);
            if (ordersAmount == null) {
                amountList.add(0);
            } else {
                amountList.add((int) Math.round(ordersAmount));
            }

        }
        chartAddUser.setValue(addUserList);
        chartAmount.setValue(amountList);
        adminChartVO.setChartAddUser(chartAddUser);
        adminChartVO.setChartAmount(chartAmount);

        return adminChartVO;
    }
}
