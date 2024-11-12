package com.atey.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.atey.constant.DeletedConstant;
import com.atey.dto.ShoppingCartDTO;
import com.atey.entity.ShoppingCart;
import com.atey.mapper.ShoppingCartMapper;
import com.atey.service.IShoppingCartService;
import com.atey.vo.ShoppingCartVO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 购物车 服务实现类
 * </p>
 *
 * @author atey
 * @since 2024-11-07
 */
@Service
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart> implements IShoppingCartService {
    /**
     * 添加到购物车
     *
     * @param shoppingCartDTO
     */
    @Override
    public void saveShopping(ShoppingCartDTO shoppingCartDTO) {
        ShoppingCart shoppingCart = new ShoppingCart();
        BeanUtil.copyProperties(shoppingCartDTO, shoppingCart);
        shoppingCart.setDeleted(DeletedConstant.NOT_DELETED);
        shoppingCart.setCreateTime(LocalDateTime.now());
        shoppingCart.setUpdateTime(LocalDateTime.now());

        //计算总价
        double amount = shoppingCartDTO.getUnitPrice() * shoppingCartDTO.getWeight();
        BigDecimal bd = BigDecimal.valueOf(amount);
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        amount = bd.doubleValue();
        shoppingCart.setAmount(amount);

        this.save(shoppingCart);
    }

    /**
     * 查询用户的购物车数据
     *
     * @param id
     * @return
     */
    @Override
    public List<ShoppingCartVO> queryShoppingCart(Integer id) {

        List<ShoppingCart> list = lambdaQuery()
                .eq(ShoppingCart::getUserId, id)
                .eq(ShoppingCart::getDeleted, DeletedConstant.NOT_DELETED)
                .list();

        return BeanUtil.copyToList(list, ShoppingCartVO.class);
    }

    /**
     * 删除购物车数据
     *
     * @param id
     */
    @Override
    public void delete(Integer id) {
        lambdaUpdate()
                .eq(ShoppingCart::getId, id)
                .set(ShoppingCart::getDeleted, DeletedConstant.DELETED)
                .update();
    }
}
