package com.atey.controller.user;


import com.atey.dto.MarketConditionDTO;
import com.atey.dto.MarketProducesDTO;
import com.atey.entity.MarketProduces;
import com.atey.result.Result;
import com.atey.service.IMarketProducesService;
import com.atey.vo.MarketProducesVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 市场上架商品 前端控制器
 * </p>
 *
 * @author atey
 * @since 2024-10-23
 */
@RestController
@RequestMapping("/user/marketProduces")
@Slf4j
@AllArgsConstructor
@Api(tags="市场产品相关接口")
public class MarketProducesController {
    private final IMarketProducesService marketProducesService;

    /**
     * 上架产品
     * @param marketProducesDTO
     * @return
     */
    @PostMapping("/save")
    @ApiOperation("上架产品")
    public Result save(@RequestBody MarketProducesDTO marketProducesDTO) {
        log.info("上架产品{}", marketProducesDTO);
        marketProducesService.saveMarketProduces(marketProducesDTO);
        return Result.success();
    }

    /**
     * 查询所有上架产品
     * @param string
     * @return
     */
    @GetMapping("/all")
    @ApiOperation("查询上架产品")
    public Result<List<MarketProducesVO>> queryAll(MarketConditionDTO marketConditionDTO){
        log.info("查询上架产品{}", marketConditionDTO);
        return marketProducesService.queryAll(marketConditionDTO);
    }

    @GetMapping("/{id}")
    @ApiOperation("根据id查询上架产品")
    public Result<MarketProducesVO> getById(@PathVariable Integer id) {
        log.info("根据id查询上架产品{}",id);
        return Result.success(marketProducesService.getByIdMP(id));
    }
}
