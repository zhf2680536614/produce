package com.atey.vo;

import com.atey.entity.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserVO extends User {
    @Serial
    private static final long serialVersionUID = 3275007317635527734L;
}
