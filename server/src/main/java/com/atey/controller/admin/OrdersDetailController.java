package com.atey.controller.admin;


import com.atey.result.Result;
import com.atey.service.IOrdersDetailService;
import com.atey.vo.OrdersDetailVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
@RestController("AdminOrdersDetailsController")
@RequestMapping("/admin/ordersDetail")
@RequiredArgsConstructor
@Slf4j
@Api(tags="订单明细相关接口")
public class OrdersDetailController {
    private final IOrdersDetailService ordersDetailService;

    /**
     * 根据id查询订单明细
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @ApiOperation("根据id查询订单明细")
    public Result<OrdersDetailVO> getById(@PathVariable Long id) {
        log.info("根据id查询订单明细{}", id);
        OrdersDetailVO userVO = ordersDetailService.getById(id);
        return Result.success(userVO);
    }

}
