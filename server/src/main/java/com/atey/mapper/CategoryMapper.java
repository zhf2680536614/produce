package com.atey.mapper;

import com.atey.entity.Category;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 农产品分类 Mapper 接口
 * </p>
 *
 * @author atey
 * @since 2024-10-23
 */
@Mapper
public interface CategoryMapper extends BaseMapper<Category> {
    void update(Category category);
}
