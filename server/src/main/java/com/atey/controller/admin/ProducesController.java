package com.atey.controller.admin;


import com.atey.dto.PageDTO;
import com.atey.dto.ProducesDTO;
import com.atey.query.ProducesQuery;
import com.atey.result.Result;
import com.atey.service.IProducesService;
import com.atey.vo.ProducesVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Delete;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 农产品 前端控制器
 * </p>
 *
 * @author atey
 * @since 2024-10-23
 */
@RestController("adminProducesController")
@RequestMapping("/admin/produces")
@Api(tags="管理端产品管理相关接口")
@Slf4j
@AllArgsConstructor
public class ProducesController {
    private final IProducesService producesService;

    /**
     * 产品分页查询
     * @param producesQuery
     * @return
     */
    @GetMapping("/page")
    @ApiOperation("产品分页查询")
    @Cacheable(cacheNames = "producesCache",key = "#producesQuery.toString() + '-' + #producesQuery.pageNo.toString()")
    public Result<PageDTO<ProducesVO>> page(ProducesQuery producesQuery){
        log.info("产品分页查询{}", producesQuery.toString());
        return producesService.queryProduces(producesQuery);
    }

    /**
     * 新增产品
     * @param producesDTO
     * @return
     */
    @PostMapping("/save")
    @ApiOperation("新增产品")
    @CacheEvict(cacheNames = "producesCache",allEntries = true)
    public Result save(@RequestBody ProducesDTO producesDTO) {
        log.info("新增产品{}", producesDTO);
        producesService.saveProduces(producesDTO);
        return Result.success();
    }

    /**
     * 删除产品
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    @ApiOperation("删除产品")
    @CacheEvict(cacheNames = "producesCache",allEntries = true)
    public Result delete(@PathVariable Integer id) {
        log.info("删除产品{}",id);
        producesService.deleteProduces(id);
        return Result.success();
    }

    /**
     * 根据id查询产品
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @ApiOperation("根据id查询产品")
    public Result<ProducesVO> get(@PathVariable Integer id) {
        log.info("根据id查询产品{}",id);
        ProducesVO producesVO= producesService.getByIdProduces(id);
        return Result.success(producesVO);
    }

    /**
     * 修改产品
     * @param producesDTO
     * @return
     */
    @PutMapping("/update")
    @ApiOperation("修改产品")
    @CacheEvict(cacheNames = "producesCache",allEntries = true)
    public Result update(@RequestBody ProducesDTO producesDTO) {
        log.info("修改产品{}",producesDTO);
        producesService.updateProduces(producesDTO);
        return Result.success();
    }
}
