package com.atey.service.impl;

import com.atey.entity.Orders;
import com.atey.mapper.OrdersMapper;
import com.atey.service.IOrdersService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单表 服务实现类
 * </p>
 *
 * @author atey
 * @since 2024-10-23
 */
@Service
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders> implements IOrdersService {

}
