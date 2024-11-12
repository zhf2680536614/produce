package com.atey.mapper;

import com.atey.entity.Orders;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.cache.annotation.CacheEvict;

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
}
