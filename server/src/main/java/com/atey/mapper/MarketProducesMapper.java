package com.atey.mapper;

import com.atey.entity.MarketProduces;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 市场上架商品 Mapper 接口
 * </p>
 *
 * @author atey
 * @since 2024-10-23
 */
@Mapper
public interface MarketProducesMapper extends BaseMapper<MarketProduces> {

    void updateMarket(MarketProduces marketProduces);
}
