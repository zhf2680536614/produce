package com.atey.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.atey.constant.DeletedConstant;
import com.atey.constant.MessageConstant;
import com.atey.constant.StatusConstant;
import com.atey.controller.user.MarketProducesController;
import com.atey.dto.MarketConditionDTO;
import com.atey.dto.MarketProducesDTO;
import com.atey.entity.Category;
import com.atey.entity.MarketProduces;
import com.atey.entity.Produces;
import com.atey.entity.User;
import com.atey.exception.BaseException;
import com.atey.mapper.MarketProducesMapper;
import com.atey.result.Result;
import com.atey.service.IMarketProducesService;
import com.atey.vo.MarketProducesVO;
import com.atey.vo.ProducesVO;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;

/**
 * <p>
 * 市场上架商品 服务实现类
 * </p>
 *
 * @author atey
 * @since 2024-10-23
 */
@Service
public class MarketProducesServiceImpl extends ServiceImpl<MarketProducesMapper, MarketProduces> implements IMarketProducesService {

    /**
     * 上架产品
     *
     * @param marketProducesDTO
     */
    @Override
    public void saveMarketProduces(MarketProducesDTO marketProducesDTO) {

        if (marketProducesDTO.getCategory() == null) {
            throw new BaseException(MessageConstant.CHOOSE_CATEGORY);
        }

        if (marketProducesDTO.getStatus() == null) {
            throw new BaseException(MessageConstant.CHOOSE_STATUS);
        }

        MarketProduces marketProduces = new MarketProduces();
        BeanUtil.copyProperties(marketProducesDTO, marketProduces);
        marketProduces.setCreateTime(LocalDateTime.now());
        marketProduces.setUpdateTime(LocalDateTime.now());
        marketProduces.setUserId(marketProducesDTO.getUserId());
        marketProduces.setDeleted(DeletedConstant.NOT_DELETED);

        save(marketProduces);

    }

    /**
     * 查询上架产品
     *
     * @param marketConditionDTO
     * @return
     */
    @Override
    public Result<List<MarketProducesVO>> queryAll(MarketConditionDTO marketConditionDTO) {

        String name = marketConditionDTO.getName();
        String username = marketConditionDTO.getUsername();
        Short order = marketConditionDTO.getOrder();
        Short orderKind = marketConditionDTO.getOrderKind();
        Integer category = marketConditionDTO.getCategory();

        User user = new User();
        //根据用户姓名查询用户id
        if (StrUtil.isNotBlank(username) && !Objects.equals(username, "")) {
            user = Db.lambdaQuery(User.class)
                    .eq(username != null, User::getUsername, username)
                    .eq(User::getDeleted, DeletedConstant.NOT_DELETED)
                    .eq(User::getStatus, StatusConstant.ENABLE)
                    .one();
        }

        Integer userId = -1;
        if (BeanUtil.isNotEmpty(user)) {
            userId = user.getId();
        }


        LambdaQueryChainWrapper<MarketProduces> eq = lambdaQuery()
                .eq(MarketProduces::getDeleted, DeletedConstant.NOT_DELETED)
                .eq(MarketProduces::getStatus, StatusConstant.ENABLE)
                .eq(category != null, MarketProduces::getCategory, category)
                .like(name != null, MarketProduces::getName, name)
                .eq(username != null, MarketProduces::getUserId, userId);


        if (orderKind == 1) {

            if (order != null) {
                if (order == 1) {
                    eq.orderByAsc(MarketProduces::getUnitPrice);
                }
                if (order == 2) {
                    eq.orderByAsc(MarketProduces::getUnitPrice);
                }
                if (order == 3) {
                    eq.orderByAsc(MarketProduces::getWeight);
                }
            } else {
                eq.orderByAsc(MarketProduces::getCreateTime);
            }
        }

        if (orderKind == 2) {
            if (order != null) {
                if (order == 1) {
                    eq.orderByDesc(MarketProduces::getUnitPrice);
                }
                if (order == 2) {
                    eq.orderByDesc(MarketProduces::getUnitPrice);
                }
                if (order == 3) {
                    eq.orderByDesc(MarketProduces::getWeight);
                }
            } else {
                eq.orderByDesc(MarketProduces::getCreateTime);
            }
        }

        List<MarketProduces> list = eq.list();

        List<MarketProducesVO> voList = new ArrayList<>();

        for (MarketProduces marketProduces : list) {
            MarketProducesVO marketProducesVO = new MarketProducesVO();
            BeanUtil.copyProperties(marketProduces, marketProducesVO);
            //获取用户名
            User one = Db.lambdaQuery(User.class)
                    .eq(marketProduces.getUserId() != null, User::getId, marketProduces.getUserId())
                    .one();
            marketProducesVO.setUsername(one.getUsername());
            //获取分类名
            Category category1 = Db.lambdaQuery(Category.class)
                    .eq(marketProduces.getCategory() != null, Category::getId, marketProduces.getCategory())
                    .one();
            marketProducesVO.setCategory(category1.getName());
            voList.add(marketProducesVO);
        }

        return Result.success(voList);
    }

    /**
     * 根据id查询上架产品
     *
     * @param id
     * @return
     */
    @Override
    public MarketProducesVO getByIdMP(Integer id) {
        MarketProduces marketProduces = getById(id);

        Category category = Db.lambdaQuery(Category.class)
                .eq(Category::getId, marketProduces.getCategory())
                .one();
        User user = Db.lambdaQuery(User.class)
                .eq(User::getId, marketProduces.getUserId())
                .one();
        MarketProducesVO marketProducesVO = new MarketProducesVO();
        BeanUtil.copyProperties(marketProduces, marketProducesVO);
        marketProducesVO.setCategory(category.getName());
        marketProducesVO.setUsername(user.getUsername());
        return marketProducesVO;
    }

}
