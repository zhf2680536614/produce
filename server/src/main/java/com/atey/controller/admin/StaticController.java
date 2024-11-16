package com.atey.controller.admin;

import com.atey.dto.AccordionDTO;
import com.atey.dto.BoutiqueDTO;
import com.atey.dto.CycleDTO;
import com.atey.result.Result;
import com.atey.service.StaticService;
import com.atey.vo.AccordionVO;
import com.atey.vo.BoutiqueVO;
import com.atey.vo.CycleVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("AdminStaticController")
@RequestMapping("/admin/static")
@AllArgsConstructor
@Slf4j
@Api(tags="静态页面管理相关接口")
public class StaticController {

    private final StaticService staticService;

    /**
     * 获取所有手风琴静态图片
     * @return
     */
    @GetMapping("/accordion")
    @ApiOperation("获取所有手风琴静态图片")
    @Cacheable(cacheNames = "accordion", key = "#root.methodName")
    public Result<List<AccordionVO>> getAccordion(){
        log.info("获取所有手风琴静态图片");
        List<AccordionVO> list = staticService.getAccordion();
        return Result.success(list);
    }

    /**
     * 手风琴页面管理
     * @param accordionDTO
     * @return
     */
    @PutMapping("/accordion/update")
    @ApiOperation("手风琴图片管理")
    @CacheEvict(cacheNames = "accordion", allEntries = true)
    public Result accordion(@RequestBody AccordionDTO accordionDTO){
        log.info("手风琴页面管理{}",accordionDTO);
        staticService.updateAccordion(accordionDTO);
        return Result.success();
    }

    /**
     * 获取所有轮播图静态图片
     * @return
     */
    @GetMapping("/cycle")
    @ApiOperation("获取所有轮播图静态图片")
    @Cacheable(cacheNames = "cycle", key = "#root.methodName")
    public Result<List<CycleVO>> getCycle(){
        log.info("获取所有轮播图静态图片");
        List<CycleVO> list = staticService.getCycle();
        return Result.success(list);
    }

    /**
     * 轮播图图片管理
     * @param cycleDTO
     * @return
     */
    @PutMapping("/cycle/update")
    @ApiOperation("轮播图图片管理")
    @CacheEvict(cacheNames = "cycle", allEntries = true)
    public Result accordion(@RequestBody CycleDTO cycleDTO){
        log.info("轮播图图片管理{}",cycleDTO);
        staticService.updateCycle(cycleDTO);
        return Result.success();
    }

    /**
     * 获取所有轮播图静态图片
     * @return
     */
    @GetMapping("/boutique")
    @Cacheable(cacheNames = "boutique", key = "#root.methodName")
    @ApiOperation("获取所有精品展示静态图片")
    public Result<List<BoutiqueVO>> getBoutique(){
        log.info("获取所有精品展示静态图片");
        List<BoutiqueVO> list = staticService.getBoutique();
        return Result.success(list);
    }

    /**
     * 精品展示图片管理
     * @param boutiqueDTO
     * @return
     */
    @PutMapping("/boutique/update")
    @ApiOperation("精品展示图片管理")
    @CacheEvict(cacheNames = "boutique", allEntries = true)
    public Result boutique(@RequestBody BoutiqueDTO boutiqueDTO){
        log.info("精品展示图片管理{}",boutiqueDTO);
        staticService.updateBoutique(boutiqueDTO);
        return Result.success();
    }
}
