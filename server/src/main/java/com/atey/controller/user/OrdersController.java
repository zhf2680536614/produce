package com.atey.controller.user;


import com.atey.dto.OrdersDTO;
import com.atey.result.Result;
import com.atey.service.IOrdersService;
import com.atey.vo.OrdersVO;
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
 * 订单表 前端控制器
 * </p>
 *
 * @author atey
 * @since 2024-10-23
 */
@RestController("UserOrdersController")
@RequestMapping("/user/orders")
@Api(tags="订单相关接口")
@Slf4j
@AllArgsConstructor
public class OrdersController {
    private final IOrdersService ordersService;

    /**
     * 用户端新增订单
     * @param ordersDTO
     * @return
     */
    @PostMapping("/save")
    @ApiOperation("新增订单")
    public Result<OrdersVO> save(@RequestBody OrdersDTO ordersDTO){
        log.info("新增订单{}",ordersDTO);
        OrdersVO result = ordersService.saveOrders(ordersDTO);
        return Result.success(result);
    }
}
