package com.atey.query;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserOrdersQuery extends PageQuery {
    private Integer id;
    private String merchantName;
    private String producesName;
    private Integer status;
    @DateTimeFormat(pattern = ("yyyy-MM-dd HH:mm:ss"))
    private LocalDateTime startCreateTime;
    @DateTimeFormat(pattern = ("yyyy-MM-dd HH:mm:ss"))
    private LocalDateTime endCreateTime;
}
