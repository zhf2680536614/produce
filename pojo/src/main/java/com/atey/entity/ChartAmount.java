package com.atey.entity;

import lombok.Data;

import java.util.List;

@Data
public class ChartAmount {
    private List<String> data;
    private List<Integer> value;
}
