package com.atey.service;

import com.atey.dto.OrdersDTO;
import com.atey.entity.Orders;
import com.atey.vo.OrdersVO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 订单表 服务类
 * </p>
 *
 * @author atey
 * @since 2024-10-23
 */
public interface IOrdersService extends IService<Orders> {

    /**
     * 用户端新增订单
     * @param ordersDTO
     * @return
     */
    OrdersVO saveOrders(OrdersDTO ordersDTO);
}
