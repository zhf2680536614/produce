package com.atey.controller.user;


import com.atey.dto.MarketConditionDTO;
import com.atey.dto.MarketProducesDTO;
import com.atey.dto.PageDTO;
import com.atey.entity.MarketProduces;
import com.atey.query.MarketProducesQuery;
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
@Api(tags = "市场产品相关接口")
public class MarketProducesController {
    private final IMarketProducesService marketProducesService;

    /**
     * 上架产品
     *
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
     *
     * @param marketConditionDTO
     * @return
     */
    @GetMapping("/all")
    @ApiOperation("查询上架产品")
    public Result<List<MarketProducesVO>> queryAll(MarketConditionDTO marketConditionDTO) {
        log.info("查询上架产品{}", marketConditionDTO);
        return marketProducesService.queryAll(marketConditionDTO);
    }

    /**
     * 根据id查询上架产品
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @ApiOperation("根据id查询上架产品")
    public Result<MarketProducesVO> getById(@PathVariable Integer id) {
        log.info("根据id查询上架产品{}", id);
        return Result.success(marketProducesService.getByIdMP(id));
    }

    /**
     * 上架产品分页查询
     * @param marketProducesQuery
     * @return
     */
    @GetMapping("/page")
    @ApiOperation("上架产品分页查询")
    public Result<PageDTO<MarketProducesVO>> queryByCondition(MarketProducesQuery marketProducesQuery) {
        log.info("上架产品分页查询{}", marketProducesQuery);
        return marketProducesService.pageMarketProduces(marketProducesQuery);
    }

    /**
     * 修改上架产品
     * @param marketProducesDTO
     * @return
     */
    @PutMapping("/update")
    @ApiOperation("修改上架产品")
    public Result update(@RequestBody MarketProducesDTO marketProducesDTO) {
        log.info("修改上架产品{}", marketProducesDTO);
        marketProducesService.updateMarket(marketProducesDTO);
        return Result.success();
    }

    /**
     * 删除上架产品
     * @param id
     * @return
     */
    @ApiOperation("删除上架产品")
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        log.info("删除上架产品,{}",id);
        marketProducesService.delete(id);
        return Result.success();
    }
}
