package com.atey.service.impl;


import cn.hutool.core.bean.BeanUtil;
import com.atey.constant.DeletedConstant;
import com.atey.constant.StatusConstant;
import com.atey.entity.Category;
import com.atey.mapper.CategoryMapper;
import com.atey.service.ICategoryService;
import com.atey.vo.CategoryVO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    /**
     * 获取所有分类
     * @return
     */
    @Override
    public List<CategoryVO> queryCategory() {

        List<Category> list = lambdaQuery()
                .eq(Category::getStatus, StatusConstant.ENABLE)
                .eq(Category::getDeleted, DeletedConstant.NOT_DELETED)
                .list();

        List<CategoryVO> categoryVOList = new ArrayList<>();

        for (Category category : list) {
            CategoryVO categoryVO = new CategoryVO();
            BeanUtil.copyProperties(category, categoryVO);
            categoryVOList.add(categoryVO);
        }

        return categoryVOList;
    }
}
