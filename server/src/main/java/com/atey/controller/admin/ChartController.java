package com.atey.controller.admin;

import com.atey.result.Result;
import com.atey.service.ChartService;
import com.atey.vo.AdminChartVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController("AdminChartController")
@RequestMapping("/admin/chart")
@Slf4j
@RequiredArgsConstructor
@Api(tags = "数据统计相关接口")
public class ChartController {

    private final ChartService chartService;

    /**
     * 管理端仪表盘数据统计
     *
     * @return
     */
    @GetMapping("/all")
    @ApiOperation("管理端仪表盘数据统计")
    @Cacheable(cacheNames = "chartCache", key = "#start.toString() + '-' + #end.toString()")
    public Result<AdminChartVO> chart(
            @DateTimeFormat(pattern = "yyyy-MM-dd")
            LocalDate start,
            @DateTimeFormat(pattern = "yyyy-MM-dd")
            LocalDate end
    ) {
        log.info("管理端仪表盘数据统计{},{}", start, end);
        AdminChartVO adminChartVO =  chartService.chart(start,end);
        return Result.success(adminChartVO);
    }
}
