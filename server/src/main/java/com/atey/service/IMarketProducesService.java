package com.atey.service;

import com.atey.dto.MarketConditionDTO;
import com.atey.dto.MarketProducesDTO;
import com.atey.entity.MarketProduces;
import com.atey.result.Result;
import com.atey.vo.MarketProducesVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 市场上架商品 服务类
 * </p>
 *
 * @author atey
 * @since 2024-10-23
 */
public interface IMarketProducesService extends IService<MarketProduces> {

    /**
     * 上架产品
     * @param marketProducesDTO
     */
    void saveMarketProduces(MarketProducesDTO marketProducesDTO);

    /**
     * 查询所有上架产品
     * @return
     */
    Result<List<MarketProducesVO>> queryAll(MarketConditionDTO marketConditionDTO);

    /**
     * 根据id查询上架产品
     * @param id
     * @return
     */
    MarketProducesVO getByIdMP(Integer id);

}
