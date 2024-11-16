package com.atey.service;

import com.atey.dto.AccordionDTO;
import com.atey.dto.BoutiqueDTO;
import com.atey.dto.CycleDTO;
import com.atey.entity.Cycle;
import com.atey.vo.AccordionVO;
import com.atey.vo.BoutiqueVO;
import com.atey.vo.CycleVO;

import java.util.List;

public interface StaticService {

    /**
     * 手风琴图片管理
     * @param accordionDTO
     */
    void updateAccordion(AccordionDTO accordionDTO);

    /**
     * 获取所有手风琴图片
     * @return
     */
    List<AccordionVO> getAccordion();

    /**
     * 获取所有轮播图图片
     * @return
     */
    List<CycleVO> getCycle();

    /**
     * 轮播图图片管理
     * @param cycleDTO
     */
    void updateCycle(CycleDTO cycleDTO);

    /**
     * 获取所有轮播图静态图片
     * @return
     */
    List<BoutiqueVO> getBoutique();

    /**
     * 精品展示图片管理
     * @param boutiqueDTO
     */
    void updateBoutique(BoutiqueDTO boutiqueDTO);
}
