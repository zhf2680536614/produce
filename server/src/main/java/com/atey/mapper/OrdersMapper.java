package com.atey.mapper;

import com.atey.entity.Orders;
import com.atey.query.UserOrdersQuery;
import com.atey.vo.UserOrdersVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 订单表 Mapper 接口
 * </p>
 *
 * @author atey
 * @since 2024-10-23
 */
@Mapper
public interface OrdersMapper extends BaseMapper<Orders> {

    void update(Orders orders);

    List<UserOrdersVO> page(UserOrdersQuery userOrdersQuery);

    Integer total(UserOrdersQuery userOrdersQuery);
}
