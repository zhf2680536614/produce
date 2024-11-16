package com.atey.controller.user;


import com.atey.dto.OrdersDTO;
import com.atey.dto.UpdateOrdersDTO;
import com.atey.query.UserOrdersQuery;
import com.atey.result.Result;
import com.atey.service.IOrdersService;
import com.atey.vo.OrdersVO;
import com.atey.vo.UserOrdersTotalsVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.*;

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
    @CacheEvict(cacheNames = {"ordersCache", "chartCache"}, allEntries = true)
    public Result<OrdersVO> save(@RequestBody OrdersDTO ordersDTO){
        log.info("新增订单{}",ordersDTO);
        OrdersVO result = ordersService.saveOrders(ordersDTO);
        return Result.success(result);
    }

    /**
     * 修改用户收货地址
     * @param updateOrdersDTO
     * @return
     */
    @PutMapping("/update")
    @ApiOperation("修改收货地址")
    @CacheEvict(cacheNames = {"ordersCache", "chartCache"}, allEntries = true)
    public Result update(@RequestBody UpdateOrdersDTO updateOrdersDTO){
        log.info("修改用户收获地址{}",updateOrdersDTO);
        ordersService.updateOrders(updateOrdersDTO);
        return Result.success();
    }

    /**
     * 根据id查询订单
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @ApiOperation("根据id查询订单")
    public Result<OrdersVO> getOrdersById(@PathVariable Integer id){
        log.info("根据id查询订单{}",id);
        OrdersVO ordersVO = ordersService.getByIdOrders(id);
        return Result.success(ordersVO);
    }

    /**
     * 确认购买
     * @param updateOrdersDTO
     * @return
     */
    @PutMapping("/confirm")
    @ApiOperation("确认购买")
    @CacheEvict(cacheNames = {"ordersCache", "chartCache"}, allEntries = true)
    public Result confirmOrders(@RequestBody UpdateOrdersDTO updateOrdersDTO){
        log.info("订单确认购买{}",updateOrdersDTO);
        ordersService.confirmOrders(updateOrdersDTO);
        return Result.success();
    }

    /**
     * 取消订单
     * @param updateOrdersDTO
     * @return
     */
    @PutMapping("/cancel")
    @ApiOperation("取消订单")
    @CacheEvict(cacheNames = {"ordersCache", "chartCache"}, allEntries = true)
    public Result cancelOrders(@RequestBody UpdateOrdersDTO updateOrdersDTO){
        log.info("取消订单{}",updateOrdersDTO);
        ordersService.cancelOrders(updateOrdersDTO);
        return Result.success();
    }

    /**
     * 订单分页查询
     * @param userOrdersQuery
     * @return
     */
    @GetMapping("/page")
    @ApiOperation("用户端订单分页查询")
    public Result<UserOrdersTotalsVO> getOrdersPage(UserOrdersQuery userOrdersQuery){
        log.info("用户端订单分页查询{}",userOrdersQuery);
        return ordersService.pageQueryUser(userOrdersQuery);
    }

    @ApiOperation("用户端删除订单")
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id){
        log.info("用户端删除订单{}",id);
        ordersService.delete(id);
        return Result.success();
    }
}
