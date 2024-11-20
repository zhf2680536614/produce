package com.atey.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.atey.constant.DeletedConstant;
import com.atey.constant.MessageConstant;
import com.atey.constant.StatusConstant;
import com.atey.controller.user.MarketProducesController;
import com.atey.dto.MarketConditionDTO;
import com.atey.dto.MarketProducesDTO;
import com.atey.dto.PageDTO;
import com.atey.entity.*;
import com.atey.exception.BaseException;
import com.atey.mapper.MarketProducesMapper;
import com.atey.query.MarketProducesQuery;
import com.atey.result.Result;
import com.atey.service.IMarketProducesService;
import com.atey.vo.MarketProducesVO;
import com.atey.vo.ProducesVO;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
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
@RequiredArgsConstructor
public class MarketProducesServiceImpl extends ServiceImpl<MarketProducesMapper, MarketProduces> implements IMarketProducesService {
    private final MarketProducesMapper marketProducesMapper;

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

    /**
     * 上架产品分页查询
     *
     * @param marketProducesQuery
     * @return
     */
    @Override
    public Result<PageDTO<MarketProducesVO>> pageMarketProduces(MarketProducesQuery marketProducesQuery) {
        Page<MarketProduces> page = marketProducesQuery.toMpPage();
        String name = marketProducesQuery.getName();
        Integer userId = marketProducesQuery.getId();
        Integer status = marketProducesQuery.getStatus();

        LambdaQueryChainWrapper<MarketProduces> eq = lambdaQuery()
                .like(name != null, MarketProduces::getName, name)
                .eq(userId != null, MarketProduces::getUserId, userId)
                .eq(MarketProduces::getDeleted, DeletedConstant.NOT_DELETED);

        if (status != null) {
            if (status.equals(StatusConstant.ENABLE)) {
                eq.eq(MarketProduces::getStatus, StatusConstant.ENABLE);
            } else {
                eq.eq(MarketProduces::getStatus, StatusConstant.DISABLE);
            }
        }

        Page<MarketProduces> result = eq.page(page);

        return Result.success(PageDTO.of(result, MarketProducesVO.class));
    }


    /**
     * 修改上架产品
     *
     * @param marketProducesDTO
     */
    @Override
    public void updateMarket(MarketProducesDTO marketProducesDTO) {

        //判断该商品是否为秒杀产品
        //如果是，则禁止修改
        MarketProducesPlus marketProducesPlus = Db.lambdaQuery(MarketProducesPlus.class)
                .eq(MarketProducesPlus::getId, marketProducesDTO.getId())
                .one();
        if (BeanUtil.isNotEmpty(marketProducesPlus)) {

            LocalDateTime localDateTime = marketProducesPlus.getCreateTime().plusMinutes(1);
            int hour = localDateTime.getHour();
            int minute = localDateTime.getMinute();
            int second = localDateTime.getSecond();

            throw new BaseException("该产品为秒杀产品，不可修改，请在" +" "+ hour + " : " + minute + " : " + second +" "+ "后重试");
        }

        Integer status = marketProducesDTO.getStatus();
        Double weight = marketProducesDTO.getWeight();
        Integer id = marketProducesDTO.getId();

        MarketProduces marketProduces = new MarketProduces();
        marketProduces.setId(id);
        marketProduces.setStatus(status);
        marketProduces.setWeight(weight);
        marketProduces.setUpdateTime(LocalDateTime.now());

        marketProducesMapper.updateMarket(marketProduces);
    }

    /**
     * 删除上架产品
     *
     * @param id
     */
    @Override
    public void delete(Integer id) {

        //判断该商品是否为秒杀产品
        //如果是，则禁止修改
        MarketProducesPlus marketProducesPlus = Db.lambdaQuery(MarketProducesPlus.class)
                .eq(MarketProducesPlus::getId, id)
                .one();
        if (BeanUtil.isNotEmpty(marketProducesPlus)) {

            LocalDateTime localDateTime = marketProducesPlus.getCreateTime().plusMinutes(1);
            int hour = localDateTime.getHour();
            int minute = localDateTime.getMinute();
            int second = localDateTime.getSecond();

            throw new BaseException("该产品为秒杀产品，不可修改，请在" +" "+ hour + " : " + minute + " : " + second +" "+ "后重试");
        }

        MarketProduces marketProduces = new MarketProduces();
        marketProduces.setId(id);
        marketProduces.setDeleted(DeletedConstant.DELETED);
        marketProduces.setUpdateTime(LocalDateTime.now());

        marketProducesMapper.updateMarket(marketProduces);
    }
}
