package com.atey.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.atey.constant.DefaultConstant;
import com.atey.constant.DeletedConstant;
import com.atey.constant.OrderStatus;
import com.atey.dto.OrdersDTO;
import com.atey.entity.AddressBook;
import com.atey.entity.Orders;
import com.atey.mapper.OrdersMapper;
import com.atey.service.IOrdersService;
import com.atey.vo.OrdersVO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

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

    /**
     * 用户端新增订单
     * @param ordersDTO
     * @return
     */
    @Override
    public OrdersVO saveOrders(OrdersDTO ordersDTO) {
        Integer userId = ordersDTO.getUserId();
        String username = ordersDTO.getUsername();
        String merchantName = ordersDTO.getMerchantName();

        //利用时间戳生成订单号
        long current = System.currentTimeMillis();
        String orderNumber = String.valueOf(current);

        //查询该用户的默认地址
        AddressBook address = Db.lambdaQuery(AddressBook.class)
                .eq(AddressBook::getUserId, userId)
                .eq(AddressBook::getIsDefault, DefaultConstant.IS_DEFAULT)
                .one();
        Integer addressBookId = address.getId();
        String consigneeName = address.getConsigneeName();
        String location = address.getLocation();
        String phoneNumber = address.getConsigneePhoneNumber();

        Orders orders = new Orders();
        orders.setOrderNumber(orderNumber);
        orders.setMerchantName(merchantName);
        orders.setUserId(userId);
        orders.setUsername(username);
        orders.setAddressBookId(addressBookId);
        orders.setConsigneeName(consigneeName);
        orders.setAddressBookName(location);
        orders.setPhoneNumber(phoneNumber);
        orders.setStatus(OrderStatus.waitConfirm);
        orders.setDeleted(DeletedConstant.NOT_DELETED);
        orders.setCreateTime(LocalDateTime.now());
        orders.setUpdateTime(LocalDateTime.now());

        save(orders);

        //查询订单id
        Orders one = lambdaQuery()
                .eq(Orders::getOrderNumber, orderNumber)
                .one();
        OrdersVO ordersVO = new OrdersVO();
        BeanUtil.copyProperties(one, ordersVO);
        return ordersVO;
    }
}
