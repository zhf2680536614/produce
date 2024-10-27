package com.atey.query;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
public class CategoryQuery extends PageQuery{
    private String name;
    @DateTimeFormat(pattern = ("yyyy-MM-dd HH:mm:ss"))
    private LocalDateTime startCreateTime;
    @DateTimeFormat(pattern = ("yyyy-MM-dd HH:mm:ss"))
    private LocalDateTime endCreateTime;
}
