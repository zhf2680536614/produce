package com.atey.service;

import com.atey.dto.ShoppingCartDTO;
import com.atey.entity.ShoppingCart;
import com.atey.vo.ShoppingCartVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 购物车 服务类
 * </p>
 *
 * @author atey
 * @since 2024-11-07
 */
public interface IShoppingCartService extends IService<ShoppingCart> {

    /**
     * 添加到购物车
     * @param shoppingCartDTO
     */
    void saveShopping(ShoppingCartDTO shoppingCartDTO);

    /**
     * 查询用户的购物车数据
     * @param id
     * @return
     */
    List<ShoppingCartVO> queryShoppingCart(Integer id);

    /**
     * 删除购物车数据
     * @param id
     */
    void delete(Integer id);
}
