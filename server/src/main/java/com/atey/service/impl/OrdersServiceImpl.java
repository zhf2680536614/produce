package com.atey.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.atey.constant.DefaultConstant;
import com.atey.constant.DeletedConstant;
import com.atey.constant.OrderStatus;
import com.atey.dto.OrdersDTO;
import com.atey.dto.PageDTO;
import com.atey.dto.UpdateOrdersDTO;
import com.atey.entity.*;
import com.atey.mapper.OrdersMapper;
import com.atey.query.OrdersQuery;
import com.atey.query.UserOrdersQuery;
import com.atey.result.Result;
import com.atey.service.IOrdersService;
import com.atey.vo.OrdersVO;
import com.atey.vo.UserOrdersTotalsVO;
import com.atey.vo.UserOrdersVO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * <p>
 * 订单表 服务实现类
 * </p>
 *
 * @author atey
 * @since 2024-10-23
 */
@Service
@RequiredArgsConstructor
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders> implements IOrdersService {
    private final OrdersMapper ordersMapper;

    /**
     * 用户端新增订单
     *
     * @param ordersDTO
     * @return
     */
    @Override
    public OrdersVO saveOrders(OrdersDTO ordersDTO) {
        Integer userId = ordersDTO.getUserId();
        String username = ordersDTO.getUsername();
        String merchantName = ordersDTO.getMerchantName();
        Integer merchantId = ordersDTO.getMerchantId();

        //利用UUID生成订单号
        UUID uuid = UUID.randomUUID();
        // 将UUID转换为字符串，并替换掉其中的'-'字符，得到一个更简洁的订单号形式

        String orderNumber = uuid.toString().replace("-", "");

        //查询该用户的默认地址
        AddressBook address = Db.lambdaQuery(AddressBook.class)
                .eq(AddressBook::getUserId, userId)
                .eq(AddressBook::getIsDefault, DefaultConstant.IS_DEFAULT)
                .eq(AddressBook::getDeleted, DeletedConstant.NOT_DELETED)
                .one();
        Integer addressBookId = address.getId();
        String consigneeName = address.getConsigneeName();
        String location = address.getLocation();
        String phoneNumber = address.getConsigneePhoneNumber();

        Orders orders = new Orders();
        orders.setOrderNumber(orderNumber);
        orders.setMerchantId(merchantId);
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
     *
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
     *
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
     *
     * @param updateOrdersDTO
     */
    @Override
    @Transactional
    public synchronized void confirmOrders(UpdateOrdersDTO updateOrdersDTO) {
        Orders orders = new Orders();
        orders.setId(updateOrdersDTO.getId());
        orders.setRemark(updateOrdersDTO.getRemark());
        //设置订单已完成
        orders.setStatus(OrderStatus.confirm);
        orders.setCompleteTime(LocalDateTime.now());
        //更新订单
        ordersMapper.update(orders);

        //更改订单明细
        Db.lambdaUpdate(OrdersDetail.class)
                .eq(OrdersDetail::getOrdersId, orders.getId())
                .set(OrdersDetail::getStatus, OrderStatus.confirm)
                .update();

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
        double resultWeight = marketWeight - weight;

        BigDecimal bd = new BigDecimal(resultWeight);
        resultWeight = bd.setScale(2, RoundingMode.HALF_UP).doubleValue();

        //更新库存
        Db.lambdaUpdate(MarketProduces.class)
                .eq(MarketProduces::getId, marketProducesId)
                .set(MarketProduces::getWeight, resultWeight)
                .update();

        //更新后查询该上架产品的重量，如果 = 0，则逻辑删除
        MarketProduces one1 = Db.lambdaQuery(MarketProduces.class)
                .eq(MarketProduces::getId, marketProducesId)
                .one();
        if (one1.getWeight() == 0) {
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
    @CacheEvict(cacheNames = "ordersCache", allEntries = true)
    public void cancelOrders(UpdateOrdersDTO updateOrdersDTO) {
        Integer id = updateOrdersDTO.getId();
        Orders orders = new Orders();
        orders.setId(id);
        orders.setCancelTime(LocalDateTime.now());
        orders.setStatus(OrderStatus.cancel);
        orders.setUpdateTime(LocalDateTime.now());
        ordersMapper.update(orders);

        Db.lambdaUpdate(OrdersDetail.class)
                .eq(OrdersDetail::getOrdersId, orders.getId())
                .set(OrdersDetail::getStatus, OrderStatus.cancel)
                .update();
    }

    /**
     * 订单分页查询
     *
     * @param ordersQuery
     * @return
     */
    @Override
    @CachePut(value = "ordersCache", key = "#ordersQuery.toString() + '-' + #ordersQuery.pageNo.toString()")
    public Result<PageDTO<OrdersVO>> pageQuery(OrdersQuery ordersQuery) {
        String username = ordersQuery.getUsername();
        String phoneNumber = ordersQuery.getPhoneNumber();
        Integer status = ordersQuery.getStatus();
        LocalDateTime startCreateTime = ordersQuery.getStartCreateTime();
        LocalDateTime endCreateTime = ordersQuery.getEndCreateTime();

        Page<Orders> page = ordersQuery.toMpPage();

        Page<Orders> result = lambdaQuery()
                .like(username != null, Orders::getUsername, username)
                .eq(phoneNumber != null, Orders::getPhoneNumber, phoneNumber)
                .eq(status != null, Orders::getStatus, status)
                .between(startCreateTime != null && endCreateTime != null, Orders::getCreateTime, startCreateTime, endCreateTime)
                .orderByDesc(Orders::getCreateTime)
                .page(page);

        return Result.success(PageDTO.of(result, OrdersVO.class));
    }

    /**
     * 用户端订单分页查询
     *
     * @param userOrdersQuery
     * @return
     */
    @Override
    public Result<UserOrdersTotalsVO> pageQueryUser(UserOrdersQuery userOrdersQuery) {
        //计算pageNo
        Integer pageNo = userOrdersQuery.getPageNo();
        Integer pageSize = userOrdersQuery.getPageSize();
        pageNo = pageSize * (pageNo - 1);
        userOrdersQuery.setPageNo(pageNo);
        List<UserOrdersVO> list = ordersMapper.page(userOrdersQuery);
        UserOrdersTotalsVO totalsVO = new UserOrdersTotalsVO();
        totalsVO.setUserOrdersTotals(list);
        Integer total = ordersMapper.total(userOrdersQuery);
        totalsVO.setTotal(total);
        return Result.success(totalsVO);
    }

    /**
     * 用户端删除订单
     * @param id
     */
    @Override
    public void delete(Integer id) {
        Orders one = lambdaQuery()
                .eq(Orders::getId, id)
                .one();
        one.setDeleted(DeletedConstant.DELETED);
        one.setUpdateTime(LocalDateTime.now());
        ordersMapper.update(one);
    }
}
