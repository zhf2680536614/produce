package com.atey.dto;

import com.atey.query.PageQuery;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserDTO extends PageQuery {
    private String username;
    private String phoneNumber;
    private LocalDateTime startCreateTime;
    private LocalDateTime endCreateTime;
}
