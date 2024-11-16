package com.atey.service;

import com.atey.vo.AdminChartVO;

/**
 * 管理端仪表盘数据统计
 *
 * @return
 */
public interface ChartService {
    AdminChartVO chart(String start, String end);
}
