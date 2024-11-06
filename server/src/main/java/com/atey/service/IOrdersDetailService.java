package com.atey.service;

import com.atey.dto.OrdersDetailsDTO;
import com.atey.entity.OrdersDetail;
import com.atey.vo.OrdersDetailVO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 订单明细表 服务类
 * </p>
 *
 * @author atey
 * @since 2024-10-23
 */
public interface IOrdersDetailService extends IService<OrdersDetail> {

    /**
     * 新增订单明细表
     * @param ordersDetailsDTO
     */
    OrdersDetailVO saveOrdersDetails(OrdersDetailsDTO ordersDetailsDTO);
}
