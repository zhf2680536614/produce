package com.atey.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ArrayUtil;
import com.atey.constant.OrderStatus;
import com.atey.dto.OrdersDetailsDTO;
import com.atey.entity.ChartCategory;
import com.atey.entity.MarketProduces;
import com.atey.entity.Orders;
import com.atey.entity.OrdersDetail;
import com.atey.mapper.OrdersDetailMapper;
import com.atey.service.IOrdersDetailService;
import com.atey.vo.ChartCategoryVO;
import com.atey.vo.OrdersDetailVO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 订单明细表 服务实现类
 * </p>
 *
 * @author atey
 * @since 2024-10-23
 */
@Service
@RequiredArgsConstructor
public class OrdersDetailServiceImpl extends ServiceImpl<OrdersDetailMapper, OrdersDetail> implements IOrdersDetailService {
    private final OrdersDetailMapper ordersDetailMapper;

    /**
     * 新增订单明细表
     *
     * @param ordersDetailsDTO
     */
    @Override
    public OrdersDetailVO saveOrdersDetails(OrdersDetailsDTO ordersDetailsDTO) {
        Integer ordersId = ordersDetailsDTO.getOrdersId();
        String produceName = ordersDetailsDTO.getProduceName();
        String produceCategory = ordersDetailsDTO.getProduceCategory();
        Double weight = ordersDetailsDTO.getWeight();
        Double unitPrice = ordersDetailsDTO.getUnitPrice();

        //获取订单号
        Orders one = Db.lambdaQuery(Orders.class)
                .eq(Orders::getId, ordersId)
                .one();
        String ordersNumber = one.getOrderNumber();
        Integer merchantId = one.getMerchantId();
        MarketProduces marketProduces = Db.lambdaQuery(MarketProduces.class)
                .eq(MarketProduces::getId, merchantId)
                .one();
        String image = marketProduces.getImage();
        OrdersDetail ordersDetail = new OrdersDetail();
        ordersDetail.setOrdersId(ordersId);
        ordersDetail.setOrdersNumber(ordersNumber);
        ordersDetail.setProduceName(produceName);
        ordersDetail.setProduceCategory(produceCategory);
        ordersDetail.setUnitPrice(unitPrice);
        ordersDetail.setProduceWeight(weight);
        ordersDetail.setImage(image);
        ordersDetail.setStatus(OrderStatus.waitConfirm);
        ordersDetail.setCreateTime(LocalDateTime.now());
        ordersDetail.setUpdateTime(LocalDateTime.now());

        double amount = weight * unitPrice;
        BigDecimal bd = new BigDecimal(amount);
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        amount = bd.doubleValue();
        ordersDetail.setAmount(amount);

        save(ordersDetail);

        OrdersDetailVO ordersDetailVO = new OrdersDetailVO();
        BeanUtil.copyProperties(ordersDetail, ordersDetailVO);
        return ordersDetailVO;
    }

    /**
     * 分类销售额统计
     *
     * @return
     */
    @Override
    public ChartCategoryVO chartCategory() {
        List<ChartCategory> list = ordersDetailMapper.chartCategory();

        ArrayUtil.isEmpty(list);
        List<String> categoryList = new ArrayList<>();
        List<Integer> amountList = new ArrayList<>();
        if (list.size() < 10) {
            for (ChartCategory cc : list) {
                categoryList.add(cc.getCategory());
                amountList.add(cc.getAmount());
            }
        } else {
            for (int i = 0; i < 10; i++) {
                categoryList.add(list.get(i).getCategory());
                amountList.add(list.get(i).getAmount());
            }
        }

        ChartCategoryVO chartVO = new ChartCategoryVO();
        chartVO.setCategory(categoryList);
        chartVO.setAmount(amountList);
        return chartVO;
    }

    /**
     * 根据id查询订单明细
     *
     * @param id
     * @return
     */
    @Override
    public OrdersDetailVO getById(Long id) {
        OrdersDetail ordersDetail = lambdaQuery()
                .eq(OrdersDetail::getOrdersId, id)
                .one();
        OrdersDetailVO ordersDetailVO = new OrdersDetailVO();
        BeanUtil.copyProperties(ordersDetail, ordersDetailVO);
        return ordersDetailVO;
    }
}
