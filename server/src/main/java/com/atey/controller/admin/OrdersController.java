package com.atey.controller.admin;


import com.atey.dto.OrdersDTO;
import com.atey.dto.PageDTO;
import com.atey.dto.UpdateOrdersDTO;
import com.atey.query.OrdersQuery;
import com.atey.result.Result;
import com.atey.service.IOrdersService;
import com.atey.vo.OrdersVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 订单表 前端控制器
 * </p>
 *
 * @author atey
 * @since 2024-10-23
 */
@RestController("AdminOrdersController")
@RequestMapping("/admin/orders")
@Api(tags="订单相关接口")
@Slf4j
@AllArgsConstructor
public class OrdersController {
    private final IOrdersService ordersService;

    /**
     * 订单分页查询
     * @param ordersQuery
     * @return
     */
    @GetMapping("/page")
    @ApiOperation("订单分页查询")
    @Cacheable(cacheNames = "ordersCache", key = "#ordersQuery.toString() + '-' + #ordersQuery.pageNo.toString()")
    public Result<PageDTO<OrdersVO>> getOrdersPage(OrdersQuery ordersQuery){
        log.info("订单分页查询{}",ordersQuery);
        return ordersService.pageQuery(ordersQuery);
    }
}
