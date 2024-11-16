package com.atey.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.atey.dto.AccordionDTO;
import com.atey.dto.BoutiqueDTO;
import com.atey.dto.CycleDTO;
import com.atey.entity.Accordion;
import com.atey.entity.Boutique;
import com.atey.entity.Cycle;
import com.atey.service.StaticService;
import com.atey.vo.AccordionVO;
import com.atey.vo.BoutiqueVO;
import com.atey.vo.CycleVO;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StaticServiceImpl implements StaticService {

    /**
     * 手风琴图片管理
     * @param accordionDTO
     */
    @Override
    public void updateAccordion(AccordionDTO accordionDTO) {
        Db.lambdaUpdate(Accordion.class)
                .eq(Accordion::getId, accordionDTO.getId())
                .set(Accordion::getImage, accordionDTO.getImage())
                .update();
    }

    /**
     * 获取所有手风琴图片
     * @return
     */
    @Override
    public List<AccordionVO> getAccordion() {
        List<Accordion> list = Db.lambdaQuery(Accordion.class).orderByAsc(Accordion::getId)
                .list();
        return BeanUtil.copyToList(list, AccordionVO.class);
    }

    /**
     * 获取所有轮播图片
     * @return
     */
    @Override
    public List<CycleVO> getCycle() {
        List<Cycle> list = Db.lambdaQuery(Cycle.class).orderByAsc(Cycle::getId)
                .list();
        return BeanUtil.copyToList(list, CycleVO.class);
    }

    /**
     * 轮播图图片管理
     * @param cycleDTO
     */
    @Override
    public void updateCycle(CycleDTO cycleDTO) {
        Db.lambdaUpdate(Cycle.class)
                .eq(Cycle::getId, cycleDTO.getId())
                .set(Cycle::getImage, cycleDTO.getImage())
                .update();
    }

    /**
     * 获取所有轮播图静态图片
     * @return
     */
    @Override
    public List<BoutiqueVO> getBoutique() {
        List<Boutique> list = Db.lambdaQuery(Boutique.class).orderByAsc(Boutique::getId)
                .list();
        return BeanUtil.copyToList(list, BoutiqueVO.class);
    }

    /**
     * 精品展示图片管理
     * @param boutiqueDTO
     */
    @Override
    public void updateBoutique(BoutiqueDTO boutiqueDTO) {
        Db.lambdaUpdate(Boutique.class)
                .eq(Boutique::getId, boutiqueDTO.getId())
                .set(Boutique::getImage, boutiqueDTO.getImage())
                .update();
    }
}
