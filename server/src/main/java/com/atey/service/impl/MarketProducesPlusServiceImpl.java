package com.atey.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.atey.constant.DefaultConstant;
import com.atey.constant.DeletedConstant;
import com.atey.constant.OrderStatus;
import com.atey.dto.UpdateOrdersDTO;
import com.atey.entity.MarketProduces;
import com.atey.entity.MarketProducesPlus;
import com.atey.entity.Orders;
import com.atey.entity.OrdersDetail;
import com.atey.mapper.MarketProducesPlusMapper;
import com.atey.mapper.OrdersMapper;
import com.atey.result.Result;
import com.atey.service.IMarketProducesPlusService;
import com.atey.vo.MPLVO;
import com.atey.vo.MarketProducesPlusVO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 市场秒杀产品 服务实现类
 * </p>
 *
 * @author atey
 * @since 2024-11-09
 */
@Service
@RequiredArgsConstructor
public class MarketProducesPlusServiceImpl extends ServiceImpl<MarketProducesPlusMapper, MarketProducesPlus> implements IMarketProducesPlusService {
    private final OrdersMapper ordersMapper;

    /**
     * 获取秒杀产品
     *
     * @return
     */
    @Override
    public MPLVO getPlus() {

        List<MarketProducesPlus> marketProducesPluses = lambdaQuery()
                .eq(MarketProducesPlus::getDeleted, DeletedConstant.NOT_DELETED)
                .list();

        //构建倒计时
        //截止时间为创建时间 + 3分钟
        LocalDateTime createTime = marketProducesPluses.get(0).getCreateTime().plusMinutes(1);
        long date = createTime.toInstant(ZoneOffset.of("+8")).toEpochMilli();
        long temp = LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli();
        date = date - temp;

        MPLVO mplVO = new MPLVO();
        mplVO.setDate(date);
        mplVO.setList(BeanUtil.copyToList(marketProducesPluses, MarketProducesPlusVO.class));

        return mplVO;
    }

    /**
     * 确认购买
     *
     * @param updateOrdersDTO
     */
    @Override
    @Transactional
    public synchronized void confirmOrders(UpdateOrdersDTO updateOrdersDTO) {

        Orders orders = new Orders();
        orders.setId(updateOrdersDTO.getId());
        orders.setRemark(updateOrdersDTO.getRemark());
        //设置订单已完成
        orders.setStatus(OrderStatus.confirm);
        orders.setCompleteTime(LocalDateTime.now());
        //更新订单
        ordersMapper.update(orders);

        //更改订单明细
        Db.lambdaUpdate(OrdersDetail.class)
                .eq(OrdersDetail::getOrdersId, orders.getId())
                .set(OrdersDetail::getStatus, OrderStatus.confirm)
                .update();

        //更新市场产品的库存
        //获取订单明细表中购买的重量
        OrdersDetail ordersDetail = Db.lambdaQuery(OrdersDetail.class)
                .eq(OrdersDetail::getOrdersId, orders.getId())
                .one();

        double weight = ordersDetail.getProduceWeight();
        //获取市场产品的id
        Integer marketProducesId = updateOrdersDTO.getMarketProducesId();

        //获取市场产品的库存
        MarketProduces one = Db.lambdaQuery(MarketProduces.class)
                .eq(MarketProduces::getId, marketProducesId)
                .one();
        Double marketWeight = one.getWeight();

        //计算购买之后的库存
        double resultWeight = marketWeight - weight;

        BigDecimal bd = new BigDecimal(resultWeight);
        resultWeight = bd.setScale(2, RoundingMode.HALF_UP).doubleValue();

        //更新库存
        Db.lambdaUpdate(MarketProduces.class)
                .eq(MarketProduces::getId, marketProducesId)
                .set(MarketProduces::getWeight, resultWeight)
                .update();

        //更新后查询该上架产品的重量，如果 = 0，则逻辑删除
        MarketProduces one1 = Db.lambdaQuery(MarketProduces.class)
                .eq(MarketProduces::getId, marketProducesId)
                .one();
        if (one1.getWeight() == 0) {
            Db.lambdaUpdate(MarketProduces.class)
                    .eq(MarketProduces::getId, marketProducesId)
                    .set(MarketProduces::getDeleted, DeletedConstant.DELETED)
                    .update();
        }

        one1 = Db.lambdaQuery(MarketProduces.class)
                .eq(MarketProduces::getId, marketProducesId)
                .one();

        Double weight1 = one1.getWeight();
        Integer deleted = one1.getDeleted();

        Db.lambdaUpdate(MarketProducesPlus.class)
                .eq(MarketProducesPlus::getId, marketProducesId)
                .set(MarketProducesPlus::getWeight, weight1)
                .set(MarketProducesPlus::getDeleted, deleted)
                .update();
    }

}
