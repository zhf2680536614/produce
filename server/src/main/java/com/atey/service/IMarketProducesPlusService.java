package com.atey.service;

import com.atey.dto.UpdateOrdersDTO;
import com.atey.entity.MarketProducesPlus;
import com.atey.vo.MPLVO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 市场秒杀产品 服务类
 * </p>
 *
 * @author atey
 * @since 2024-11-09
 */
public interface IMarketProducesPlusService extends IService<MarketProducesPlus> {

    /**
     * 获取秒杀产品
     * @return
     */
    MPLVO getPlus();

    /**
     * 确认购买
     * @param updateOrdersDTO
     */
    void confirmOrders(UpdateOrdersDTO updateOrdersDTO);
}
