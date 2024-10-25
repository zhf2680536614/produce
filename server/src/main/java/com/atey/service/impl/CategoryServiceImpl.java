package com.atey.service.impl;


import com.atey.entity.Category;
import com.atey.mapper.CategoryMapper;
import com.atey.service.ICategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 农产品分类 服务实现类
 * </p>
 *
 * @author atey
 * @since 2024-10-23
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements ICategoryService {

}
