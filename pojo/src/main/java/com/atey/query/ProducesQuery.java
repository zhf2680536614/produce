package com.atey.query;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
public class ProducesQuery extends PageQuery {
    private String name;
    private String origin;
    private Integer category;
    @DateTimeFormat(pattern = ("yyyy-MM-dd HH:mm:ss"))
    private LocalDateTime startCreateTime;
    @DateTimeFormat(pattern = ("yyyy-MM-dd HH:mm:ss"))
    private LocalDateTime endCreateTime;
}
