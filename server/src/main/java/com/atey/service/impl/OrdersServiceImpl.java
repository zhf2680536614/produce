package com.atey.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.atey.constant.DefaultConstant;
import com.atey.constant.DeletedConstant;
import com.atey.constant.OrderStatus;
import com.atey.dto.OrdersDTO;
import com.atey.dto.UpdateOrdersDTO;
import com.atey.entity.AddressBook;
import com.atey.entity.MarketProduces;
import com.atey.entity.Orders;
import com.atey.entity.OrdersDetail;
import com.atey.mapper.OrdersMapper;
import com.atey.service.IOrdersService;
import com.atey.vo.OrdersVO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders> implements IOrdersService {
    private final OrdersMapper ordersMapper;
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

    /**
     * 修改用户收货地址
     * @param updateOrdersDTO
     */
    @Override
    public void updateOrders(UpdateOrdersDTO updateOrdersDTO) {

        Orders orders = new Orders();
        orders.setId(updateOrdersDTO.getId());
        orders.setAddressBookId(updateOrdersDTO.getAddressBookId());
        orders.setAddressBookName(updateOrdersDTO.getAddressBookName());
        orders.setConsigneeName(updateOrdersDTO.getConsigneeName());
        orders.setPhoneNumber(updateOrdersDTO.getConsigneePhoneNumber());
        orders.setUpdateTime(LocalDateTime.now());

        ordersMapper.update(orders);
    }

    /**
     * 根据id查询订单
     * @param id
     * @return
     */
    @Override
    public OrdersVO getByIdOrders(Integer id) {
        Orders orders = getById(id);
        OrdersVO ordersVO = new OrdersVO();
        BeanUtil.copyProperties(orders, ordersVO);
        return ordersVO;
    }

    /**
     * 确认购买
     * @param updateOrdersDTO
     */
    @Override
    public void confirmOrders(UpdateOrdersDTO updateOrdersDTO) {
        Orders orders = new Orders();
        orders.setId(updateOrdersDTO.getId());
        orders.setRemark(updateOrdersDTO.getRemark());
        //设置订单已完成
        orders.setStatus(OrderStatus.confirm);
        orders.setCompleteTime(LocalDateTime.now());
        //更新订单
        ordersMapper.update(orders);

        //更新市场产品的库存
        //获取订单明细表中购买的重量
        OrdersDetail ordersDetail = Db.lambdaQuery(OrdersDetail.class)
                .eq(OrdersDetail::getOrdersId, orders.getId())
                .one();

        double weight = ordersDetail.getProduceWeight();
        //获取市场产品的id
        Integer marketProducesId = updateOrdersDTO.getMarketProducesId();

        //获取市场产品的库存
        MarketProduces one = Db.lambdaQuery(MarketProduces.class)
                .eq(MarketProduces::getId, marketProducesId)
                .one();
        Double marketWeight = one.getWeight();

        //计算购买之后的库存
        Double resultWeight = marketWeight - weight;

        //更新库存
        Db.lambdaUpdate(MarketProduces.class)
                .eq(MarketProduces::getId, marketProducesId)
                .set(MarketProduces::getWeight, resultWeight)
                .update();

        //更新后查询该上架产品的重量，如果 = 0，则逻辑删除
        MarketProduces one1 = Db.lambdaQuery(MarketProduces.class)
                .eq(MarketProduces::getId, marketProducesId)
                .one();
        if(one1.getWeight() == 0){
            Db.lambdaUpdate(MarketProduces.class)
                    .eq(MarketProduces::getId, marketProducesId)
                    .set(MarketProduces::getDeleted, DeletedConstant.DELETED)
                    .update();
        }
    }

    /**
     * 取消订单
     */
    @Override
    public void cancelOrders(UpdateOrdersDTO updateOrdersDTO) {
        Integer id = updateOrdersDTO.getId();
        Orders orders = new Orders();
        orders.setId(id);
        orders.setCancelTime(LocalDateTime.now());
        orders.setStatus(OrderStatus.cancel);
        orders.setUpdateTime(LocalDateTime.now());
        ordersMapper.update(orders);
    }
}
