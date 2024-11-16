package com.atey.controller.admin;


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
@RestController("CategoryControllerAdmin")
@RequestMapping("/admin/category")
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
    @Cacheable(cacheNames = "categoryCache", key = "#root.methodName")
    public Result<List<CategoryVO>> queryCategory() {
        log.info("查询所有分类");
        List<CategoryVO> list = categoryService.queryCategory();
        return Result.success(list);
    }

    /**
     * 分类分页查询
     *
     * @param categoryQuery
     * @return
     */
    @GetMapping("/page")
    @ApiOperation("分类分页查询")
    @Cacheable(cacheNames = "categoryCache", key = "#categoryQuery.toString() + '-' + #categoryQuery.pageNo.toString()")
    public Result<PageDTO<Category>> query(CategoryQuery categoryQuery) {
        log.info("分类分页查询{}", categoryQuery);
        return categoryService.pageQueryCategory(categoryQuery);
    }

    /**
     * 新增分类
     *
     * @param categoryDTO
     * @return
     */
    @PostMapping("/save")
    @ApiOperation("新增分类")
    @CacheEvict(cacheNames = {"categoryCache", "categoryCacheUser", "chartCache"}, allEntries = true)
    public Result save(@RequestBody CategoryDTO categoryDTO) {
        log.info("新增分类{}", categoryDTO);
        categoryService.saveCategory(categoryDTO);
        return Result.success();
    }

    /**
     * 删除分类
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    @ApiOperation("删除分类")
    @CacheEvict(cacheNames = {"categoryCache", "categoryCacheUser", "chartCache"}, allEntries = true)
    public Result delete(@PathVariable Integer id) {
        log.info("删除分类{}", id);
        categoryService.deleteCategory(id);
        return Result.success();
    }

    /**
     * 根据id查询分类
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @ApiOperation("根据id查询分类")
    public Result<Category> getById(@PathVariable Integer id) {
        log.info("根据id查询分类{}", id);
        Category category = categoryService.getById(id);
        return Result.success(category);
    }

    /**
     * 修改分类
     */
    @PutMapping("/update")
    @ApiOperation("修改分类")
    @CacheEvict(cacheNames = {"categoryCache", "categoryCacheUser", "chartCache"}, allEntries = true)
    public Result update(@RequestBody CategoryDTO categoryDTO) {
        log.info("修改分类{}", categoryDTO);
        categoryService.updateCategory(categoryDTO);
        return Result.success();
    }
}
