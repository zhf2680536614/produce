package com.atey.service;

import com.atey.vo.AdminChartVO;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 管理端仪表盘数据统计
 *
 * @return
 */
public interface ChartService {
    AdminChartVO chart(LocalDate start, LocalDate end);
}
