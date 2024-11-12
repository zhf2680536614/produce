package com.atey.service.impl;


import cn.hutool.core.bean.BeanUtil;
import com.atey.constant.DeletedConstant;
import com.atey.constant.MessageConstant;
import com.atey.constant.StatusConstant;
import com.atey.dto.CategoryDTO;
import com.atey.dto.PageDTO;
import com.atey.entity.Category;
import com.atey.entity.Produces;
import com.atey.exception.BaseException;
import com.atey.mapper.CategoryMapper;
import com.atey.query.CategoryQuery;
import com.atey.result.Result;
import com.atey.service.ICategoryService;
import com.atey.vo.CategoryVO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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
@AllArgsConstructor
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements ICategoryService {

    private final CategoryMapper categoryMapper;

    /**
     * 获取所有分类
     *
     * @return
     */
    @Override
    public List<CategoryVO> queryCategory() {

        List<Category> list = lambdaQuery()
                .eq(Category::getStatus, StatusConstant.ENABLE)
                .eq(Category::getDeleted, DeletedConstant.NOT_DELETED)
                .list();

        return BeanUtil.copyToList(list, CategoryVO.class);
    }

    /**
     * 分类分页查询
     *
     * @param categoryQuery
     * @return
     */
    @Override
    @CachePut(value="categoryCache",key="#categoryQuery.toString() + '-' + #categoryQuery.pageNo.toString()")
    public Result<PageDTO<Category>> pageQueryCategory(CategoryQuery categoryQuery) {
        String name = categoryQuery.getName();
        LocalDateTime startCreateTime = categoryQuery.getStartCreateTime();
        LocalDateTime endCreateTime = categoryQuery.getEndCreateTime();

        Page<Category> page = categoryQuery.toMpPage();

        Page<Category> result = lambdaQuery()
                .like(name != null, Category::getName, name)
                .eq(Category::getDeleted, DeletedConstant.NOT_DELETED)
                .between(startCreateTime != null && endCreateTime != null, Category::getCreateTime, startCreateTime, endCreateTime)
                .orderByDesc(Category::getCreateTime)
                .page(page);

        return Result.success(PageDTO.of(result, Category.class));
    }

    /**
     * 新增分类
     * @param categoryDTO
     */
    @Override
    public void saveCategory(CategoryDTO categoryDTO) {
        if(categoryDTO.getStatus()==null){
            throw new BaseException(MessageConstant.CHOOSE_STATUS);
        }

        Category category = new Category();
        BeanUtil.copyProperties(categoryDTO,category);
        category.setCreateTime(LocalDateTime.now());
        category.setUpdateTime(LocalDateTime.now());
        category.setDeleted(DeletedConstant.NOT_DELETED);

        //查询数据库中产品是否存在
        Category one = lambdaQuery()
                .eq(categoryDTO.getName() != null, Category::getName, categoryDTO.getName())
                .eq(Category::getDeleted, DeletedConstant.NOT_DELETED)
                .one();
        if (BeanUtil.isEmpty(one)) {
            save(category);
        } else {
            throw new BaseException(MessageConstant.CATEGORY_EXIST_SAVE);
        }
    }

    /**
     * 删除分类
     * @param id
     */
    @Override
    @Transactional
    public void deleteCategory(Integer id) {
        //判断该分类下有没有产品，如果有则删除失败
        List<Produces> list = Db.lambdaQuery(Produces.class)
                .eq(Produces::getCategory, id)
                .eq(Produces::getDeleted, DeletedConstant.NOT_DELETED)
                .list();
        if(!list.isEmpty()){
            throw new BaseException(MessageConstant.CATEGORY_PRODUCES_DELETE);
        }

        Category category = lambdaQuery()
                .eq(Category::getId, id)
                .one();
        category.setDeleted(DeletedConstant.DELETED);

        categoryMapper.update(category);
    }

    /**
     * 修改分类
     * @param categoryDTO
     */
    @Override
    @Transactional
    public void updateCategory(CategoryDTO categoryDTO) {
        //判断该分类下有没有产品，如果有则修改失败
        List<Produces> list = Db.lambdaQuery(Produces.class)
                .eq(Produces::getCategory, categoryDTO.getId())
                .eq(Produces::getDeleted, DeletedConstant.NOT_DELETED)
                .list();
        if(!list.isEmpty()){
            throw new BaseException(MessageConstant.CATEGORY_PRODUCES_UPDATE);
        }

        Category category = new Category();

        BeanUtil.copyProperties(categoryDTO, category);

        category.setUpdateTime(LocalDateTime.now());

        categoryMapper.update(category);

        //查询数据库中用户名是否重复
        List<Category> result = lambdaQuery()
                .eq(categoryDTO.getName() != null, Category::getName, categoryDTO.getName())
                .eq(Category::getDeleted, DeletedConstant.NOT_DELETED)
                .list();

        if (result.size() > 1) {
            throw new BaseException(MessageConstant.CATEGORY_EXIST_UPDATE);
        }
    }
}
