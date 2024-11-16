package com.atey.entity;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class ChartAmount implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private List<LocalDate> dataList;
    private List<Integer> value;
}
