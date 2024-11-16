package com.atey.controller.admin;

import com.atey.result.Result;
import com.atey.service.ChartService;
import com.atey.service.IOrdersDetailService;
import com.atey.vo.AdminChartVO;
import com.atey.vo.ChartCategoryVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RestController("AdminChartController")
@RequestMapping("/admin/chart")
@Slf4j
@AllArgsConstructor
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
    public Result<AdminChartVO> chart(
            String start,
            String end
    ) {
        log.info("管理端仪表盘数据统计{},{}", start, end);
        AdminChartVO adminChartVO =  chartService.chart(start,end);
        return Result.success(adminChartVO);
    }
}
