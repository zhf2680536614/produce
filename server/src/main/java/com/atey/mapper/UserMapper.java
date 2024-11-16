package com.atey.mapper;

import com.atey.entity.ChartBG;
import com.atey.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;


/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author atey
 * @since 2024-10-23
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

    /**
     * 更新用户
     * @param user
     */
    void update(User user);

    List<ChartBG> countBG();

    Integer countByMap(Map map);
}
