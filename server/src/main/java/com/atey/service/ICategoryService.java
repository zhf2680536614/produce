package com.atey.service;


import com.atey.entity.Category;
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
}
