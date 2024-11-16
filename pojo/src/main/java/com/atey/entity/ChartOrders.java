package com.atey.entity;

import lombok.Builder;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
@Builder
public class ChartOrders implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Integer status;
    private Integer count;
}
