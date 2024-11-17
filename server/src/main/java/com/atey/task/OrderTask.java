package com.atey.task;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ArrayUtil;
import com.atey.constant.DeletedConstant;
import com.atey.constant.OrderStatus;
import com.atey.constant.StatusConstant;
import com.atey.entity.MarketProduces;
import com.atey.entity.MarketProducesPlus;
import com.atey.entity.OrdersDetail;
import com.atey.mapper.MarketProducesPlusMapper;
import com.atey.mapper.OrdersMapper;
import com.atey.entity.Orders;
import com.atey.service.IMarketProducesPlusService;
import com.atey.service.IOrdersService;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.*;

@Component
@Slf4j
@AllArgsConstructor
public class OrderTask {

    private final OrdersMapper ordersMapper;
    private final IOrdersService ordersService;
    private final IMarketProducesPlusService marketProducesPlusService;
    private final MarketProducesPlusMapper marketProducesPlusMapper;

    /**
     * 自动处理未确认的订单
     */
    @Scheduled(cron = "0 * * * * *")
    @CacheEvict(cacheNames = {"ordersCache","chartCache"}, allEntries = true)
    public void operateOutTimeOrder() {
        log.info("自动处理超时未支付订单 : {}", LocalDateTime.now());

        //查询订单表中是否存在未支付且超时的订单 超时时间为15分钟
        LocalDateTime outTime = LocalDateTime.now().plusMinutes(-15);

        List<Orders> outList = ordersService.lambdaQuery()
                .eq(Orders::getStatus, OrderStatus.waitConfirm)
                .eq(Orders::getDeleted, DeletedConstant.NOT_DELETED)
                .lt(Orders::getCreateTime, outTime)
                .list();

        if (ArrayUtil.isNotEmpty(outList)) {
            for (Orders orders : outList) {
                orders.setStatus(OrderStatus.cancel);
                orders.setCancelTime(LocalDateTime.now());
                ordersMapper.update(orders);

                Db.lambdaUpdate(OrdersDetail.class)
                        .eq(OrdersDetail::getOrdersId, orders.getId())
                        .set(OrdersDetail::getStatus, OrderStatus.cancel)
                        .update();
            }

        }
    }

    //自动构造秒杀产品
    @Scheduled(cron = "0 */1 * * * *")
    @CacheEvict(cacheNames = "marketProducesPlusCache", allEntries = true)
    public void createMPL() {
        log.info("自动处理秒杀产品 : {}", LocalDateTime.now());
        //删除秒杀产品数据库数据
        List<MarketProducesPlus> marketProducesPluses = Db.lambdaQuery(MarketProducesPlus.class)
                .eq(MarketProducesPlus::getDeleted, DeletedConstant.NOT_DELETED)
                .list();
        List<Integer> idList = new ArrayList<>();
        if (!marketProducesPluses.isEmpty()) {
            for (MarketProducesPlus marketProducesPlus : marketProducesPluses) {
                idList.add(marketProducesPlus.getId());
            }
        }
        marketProducesPlusMapper.deleteByIds(idList);

        List<MarketProduces> list = Db.lambdaQuery(MarketProduces.class)
                .eq(MarketProduces::getDeleted, DeletedConstant.NOT_DELETED)
                .eq(MarketProduces::getStatus, StatusConstant.ENABLE)
                .list();

        Random r = new Random();

        //构造随机索引
        Set<Integer> set = new HashSet<>();
        while (set.size() < 8) {
            int index = r.nextInt(list.size());
            set.add(index);
        }

        List<MarketProducesPlus> listPlus = new ArrayList<>();

        for (int index : set) {
            MarketProduces marketProduces = list.get(index);
            MarketProducesPlus marketProducesPlus = new MarketProducesPlus();
            BeanUtil.copyProperties(marketProduces, marketProducesPlus);
            marketProducesPlus.setCreateTime(LocalDateTime.now());
            marketProducesPlus.setUpdateTime(LocalDateTime.now());
            listPlus.add(marketProducesPlus);
        }

        marketProducesPlusService.saveBatch(listPlus);

    }
}
