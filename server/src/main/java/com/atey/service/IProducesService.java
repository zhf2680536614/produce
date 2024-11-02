package com.atey.service;

import com.atey.dto.PageDTO;
import com.atey.dto.ProducesDTO;
import com.atey.query.ProducesQuery;
import com.atey.entity.Produces;
import com.atey.result.Result;
import com.atey.vo.ProducesVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 农产品 服务类
 * </p>
 *
 * @author atey
 * @since 2024-10-23
 */
public interface IProducesService extends IService<Produces> {

    /**
     * 新增产品
     * @param producesDTO
     * @return
     */
    void saveProduces(ProducesDTO producesDTO);

    /**
     * 产品分页查询
     * @param producesQuery
     * @return
     */
    Result<PageDTO<ProducesVO>> queryProduces(ProducesQuery producesQuery);

    /**
     * 删除产品
     * @param id
     * @return
     */
    void deleteProduces(Integer id);

    /**
     * 根据id查询产品
     * @param id
     * @return
     */
    ProducesVO getByIdProduces(Integer id);

    /**
     * 修改产品
     * @param producesDTO
     * @return
     */
    void updateProduces(ProducesDTO producesDTO);

    /**
     * 获取所有的产品
     * @return
     */
    List<ProducesVO> queryALl();
}
