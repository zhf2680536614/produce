package com.atey.service.impl;

import com.atey.constant.DeletedConstant;
import com.atey.constant.StatusConstant;
import com.atey.entity.User;
import com.atey.service.ChartService;
import com.atey.vo.AdminChartVO;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import org.springframework.stereotype.Service;

@Service
public class ChartServiceImpl implements ChartService {

    /**
     * 管理端仪表盘数据统计
     *
     * @return
     */
    @Override
    public AdminChartVO chart(String start, String end) {

        AdminChartVO adminChartVO = new AdminChartVO();
        //统计用户数量
        Long userAmount = Db.lambdaQuery(User.class)
                .eq(User::getStatus, StatusConstant.ENABLE)
                .eq(User::getDeleted, DeletedConstant.NOT_DELETED)
                .count();
        adminChartVO.setUserAmount(userAmount);

        //统计男女数量
        Long count = Db.lambdaQuery(User.class)
                .eq(User::getStatus, StatusConstant.ENABLE)
                .eq(User::getDeleted, DeletedConstant.NOT_DELETED)
                .groupBy(User::getGender)
                .count();
        System.out.println(count);

        return null;
    }
}
