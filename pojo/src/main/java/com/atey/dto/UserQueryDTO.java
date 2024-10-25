package com.atey.dto;

import com.atey.query.PageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserQueryDTO extends PageQuery {
    private String username;
    private String phoneNumber;
    @DateTimeFormat(pattern = ("yyyy-MM-dd HH:mm:ss"))
    private LocalDateTime startCreateTime;
    @DateTimeFormat(pattern = ("yyyy-MM-dd HH:mm:ss"))
    private LocalDateTime endCreateTime;
}
