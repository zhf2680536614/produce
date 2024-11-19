package com.atey.controller.user;


import com.atey.result.Result;
import com.atey.service.ICategoryService;
import com.atey.vo.CategoryVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
@RequiredArgsConstructor
public class CategoryController {
    private final ICategoryService categoryService;

    /**
     * 获取所有分类
     *
     * @return
     */
    @GetMapping("/queryCategory")
    @ApiOperation("获取所有分类")
    @Cacheable(cacheNames = "categoryCacheUser", key = "#root.methodName")
    public Result<List<CategoryVO>> queryCategory() {
        log.info("查询所有分类");
        List<CategoryVO> list = categoryService.queryCategory();
        return Result.success(list);
    }
}
