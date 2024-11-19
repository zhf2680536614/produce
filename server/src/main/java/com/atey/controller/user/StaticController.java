package com.atey.controller.user;

import com.atey.result.Result;
import com.atey.service.StaticService;
import com.atey.vo.AccordionVO;
import com.atey.vo.BoutiqueVO;
import com.atey.vo.CycleVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("UserStaticController")
@RequestMapping("/user/static")
@RequiredArgsConstructor
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

}
