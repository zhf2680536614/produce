package com.atey.controller.user;


import com.atey.dto.UpdateOrdersDTO;
import com.atey.result.Result;
import com.atey.service.IMarketProducesPlusService;
import com.atey.vo.MPLVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 市场秒杀产品 前端控制器
 * </p>
 *
 * @author atey
 * @since 2024-11-09
 */
@RestController
@RequestMapping("/user/producesPlus")
@Slf4j
@RequiredArgsConstructor
@Api(tags = "秒杀产品相关接口")
public class MarketProducesPlusController {

    private final IMarketProducesPlusService marketProducesPlusService;

    /**
     * 获取秒杀产品
     *
     * @return
     */
    @GetMapping("/all")
    public Result<MPLVO> getPlus() {
        log.info("获取所有秒杀产品");
        MPLVO mp = marketProducesPlusService.getPlus();
        return Result.success(mp);
    }

    /**
     * 秒杀产品确认购买
     *
     * @param updateOrdersDTO
     * @return
     */
    @PutMapping("/confirm")
    @ApiOperation("确认购买")
    @CacheEvict(cacheNames = {"ordersCache", "chartCache"}, allEntries = true)
    public Result confirmOrders(@RequestBody UpdateOrdersDTO updateOrdersDTO) {
        log.info("订单确认购买{}", updateOrdersDTO);
        marketProducesPlusService.confirmOrders(updateOrdersDTO);
        return Result.success();
    }
}
