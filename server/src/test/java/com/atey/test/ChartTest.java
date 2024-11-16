package com.atey.test;

import com.atey.constant.DeletedConstant;
import com.atey.constant.StatusConstant;
import com.atey.entity.User;
import com.atey.service.ChartService;
import com.atey.vo.AdminChartVO;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;

@AllArgsConstructor
class ChartTest {
    private final ChartService chartService;

    @Test
    public void test() {
        AdminChartVO adminChartVO = new AdminChartVO();
    }
}
