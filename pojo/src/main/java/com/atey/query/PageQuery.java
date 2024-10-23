package com.atey.query;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

@Data
public class PageQuery {
    //页码
    private Integer pageNo;
    //记录数
    private Integer pageSize;

    public <T> Page<T> toMpPage() {
        // 分页条件
        return Page.of(pageNo, pageSize);
    }
}
