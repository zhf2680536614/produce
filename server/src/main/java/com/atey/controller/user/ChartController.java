package com.atey.controller.user;

import com.atey.result.Result;
import com.atey.service.IOrdersDetailService;
import com.atey.vo.ChartCategoryVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("UserChartController")
@RequestMapping("/user/chart")
@Slf4j
@RequiredArgsConstructor
@Api(tags="数据统计相关接口")
public class ChartController {

    private final IOrdersDetailService ordersDetailService;

    /**
     * 分类销售统计
     * @return
     */
    @GetMapping("/category")
    @ApiOperation("统计分类销售额")
    public Result<ChartCategoryVO> chart(){
        log.info("分类销售额统计");
        ChartCategoryVO chartVO = ordersDetailService.chartCategory();
        return Result.success(chartVO);
    }
}
