package com.atey.controller.user;


import com.atey.dto.OrdersDetailsDTO;
import com.atey.entity.OrdersDetail;
import com.atey.result.Result;
import com.atey.service.IOrdersDetailService;
import com.atey.vo.OrdersDetailVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 订单明细表 前端控制器
 * </p>
 *
 * @author atey
 * @since 2024-10-23
 */
@RestController("UserOrdersDetailsController")
@RequestMapping("/user/ordersDetail")
@AllArgsConstructor
@Slf4j
@Api(tags="订单明细相关接口")
public class OrdersDetailController {
    private final IOrdersDetailService ordersDetailService;

    /**
     * 新增订单明细表
     * @param ordersDetailsDTO
     * @return
     */
    @PostMapping("/save")
    @ApiOperation("新增订单明细表")
    public Result<OrdersDetailVO> save(@RequestBody OrdersDetailsDTO ordersDetailsDTO) {
        log.info("新增订单明细表{}", ordersDetailsDTO);
        OrdersDetailVO result = ordersDetailService.saveOrdersDetails(ordersDetailsDTO);
        return Result.success(result);
    }
}
