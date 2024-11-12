package com.atey.mapper;

import com.atey.entity.ChartCategory;
import com.atey.entity.OrdersDetail;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 订单明细表 Mapper 接口
 * </p>
 *
 * @author atey
 * @since 2024-10-23
 */
@Mapper
public interface OrdersDetailMapper extends BaseMapper<OrdersDetail> {

    List<ChartCategory> chartCategory();
}
