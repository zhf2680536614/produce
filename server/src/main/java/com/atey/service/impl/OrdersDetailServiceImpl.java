package com.atey.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.atey.dto.OrdersDetailsDTO;
import com.atey.entity.Orders;
import com.atey.entity.OrdersDetail;
import com.atey.mapper.OrdersDetailMapper;
import com.atey.service.IOrdersDetailService;
import com.atey.vo.OrdersDetailVO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.handler.BeanNameUrlHandlerMapping;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

/**
 * <p>
 * 订单明细表 服务实现类
 * </p>
 *
 * @author atey
 * @since 2024-10-23
 */
@Service
public class OrdersDetailServiceImpl extends ServiceImpl<OrdersDetailMapper, OrdersDetail> implements IOrdersDetailService {

    /**
     * 新增订单明细表
     * @param ordersDetailsDTO
     */
    @Override
    public OrdersDetailVO saveOrdersDetails(OrdersDetailsDTO ordersDetailsDTO) {
        Integer ordersId = ordersDetailsDTO.getOrdersId();
        String produceName = ordersDetailsDTO.getProduceName();
        String produceCategory = ordersDetailsDTO.getProduceCategory();
        Double weight = ordersDetailsDTO.getWeight();
        Double unitPrice = ordersDetailsDTO.getUnitPrice();

        //获取订单号
        Orders one = Db.lambdaQuery(Orders.class)
                .eq(Orders::getId, ordersId)
                .one();
        String ordersNumber = one.getOrderNumber();

        OrdersDetail ordersDetail = new OrdersDetail();
        ordersDetail.setOrdersId(ordersId);
        ordersDetail.setOrdersNumber(ordersNumber);
        ordersDetail.setProduceName(produceName);
        ordersDetail.setProduceCategory(produceCategory);
        ordersDetail.setUnitPrice(unitPrice);
        ordersDetail.setProduceWeight(weight);
        ordersDetail.setCreateTime(LocalDateTime.now());
        ordersDetail.setUpdateTime(LocalDateTime.now());

        double amount = weight * unitPrice;
        BigDecimal bd = new BigDecimal(amount);
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        amount = bd.doubleValue();
        ordersDetail.setAmount(amount);

        save(ordersDetail);

        OrdersDetailVO ordersDetailVO = new OrdersDetailVO();
        BeanUtil.copyProperties(ordersDetail, ordersDetailVO);
        return ordersDetailVO;
    }
}
