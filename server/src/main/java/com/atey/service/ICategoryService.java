package com.atey.service;


import com.atey.dto.CategoryDTO;
import com.atey.dto.PageDTO;
import com.atey.entity.Category;
import com.atey.query.CategoryQuery;
import com.atey.result.Result;
import com.atey.vo.CategoryVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 农产品分类 服务类
 * </p>
 *
 * @author atey
 * @since 2024-10-23
 */
public interface ICategoryService extends IService<Category> {

    /**
     * 获取所有分类
     * @return
     */
    List<CategoryVO> queryCategory();

    /**
     * 分类分页查询
     * @param categoryQuery
     * @return
     */
    Result<PageDTO<Category>> pageQueryCategory(CategoryQuery categoryQuery);

    /**
     * 新增分类
     * @param categoryDTO
     */
    void saveCategory(CategoryDTO categoryDTO);

    /**
     * 删除分类
     * @param id
     */
    void deleteCategory(Integer id);

    /**
     * 修改分类
     * @param categoryDTO
     */
    void updateCategory(CategoryDTO categoryDTO);
}
