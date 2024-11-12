package com.atey.service;

import com.atey.dto.OrdersDTO;
import com.atey.dto.PageDTO;
import com.atey.dto.UpdateOrdersDTO;
import com.atey.entity.Orders;
import com.atey.query.OrdersQuery;
import com.atey.query.UserOrdersQuery;
import com.atey.result.Result;
import com.atey.vo.OrdersVO;
import com.atey.vo.UserOrdersVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

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

    /**
     * 修改用户收获地址
     * @param updateOrdersDTO
     */
    void updateOrders(UpdateOrdersDTO updateOrdersDTO);

    /**
     * 根据id查询订单
     * @param id
     * @return
     */
    OrdersVO getByIdOrders(Integer id);

    /**
     * 确认购买
     * @param updateOrdersDTO
     */
    void confirmOrders(UpdateOrdersDTO updateOrdersDTO);

    /**
     * 取消订单
     * @param updateOrdersDTO
     */
    void cancelOrders(UpdateOrdersDTO updateOrdersDTO);

    /**
     * 订单分页查询
     * @param ordersQuery
     * @return
     */
    Result<PageDTO<OrdersVO>> pageQuery(OrdersQuery ordersQuery);

    /**
     * 用户端订单分页查询
     * @param userOrdersQuery
     * @return
     */
    Result<PageDTO<UserOrdersVO>> pageQueryUser(UserOrdersQuery userOrdersQuery);
}
