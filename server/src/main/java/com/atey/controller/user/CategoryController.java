package com.atey.controller.user;


import com.atey.dto.CategoryDTO;
import com.atey.dto.PageDTO;
import com.atey.entity.Category;
import com.atey.query.CategoryQuery;
import com.atey.result.Result;
import com.atey.service.ICategoryService;
import com.atey.vo.CategoryVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 农产品分类 前端控制器
 * </p>
 *
 * @author atey
 * @since 2024-10-23
 */
@RestController("CategoryControllerUser")
@RequestMapping("/user/category")
@Api(tags = "管理端分类相关接口")
@Slf4j
@AllArgsConstructor
public class CategoryController {
    private final ICategoryService categoryService;

    /**
     * 获取所有分类
     *
     * @return
     */
    @GetMapping("/queryCategory")
    @ApiOperation("获取所有分类")
    public Result<List<CategoryVO>> queryCategory() {
        log.info("查询所有分类");
        List<CategoryVO> list = categoryService.queryCategory();
        return Result.success(list);
    }
}
