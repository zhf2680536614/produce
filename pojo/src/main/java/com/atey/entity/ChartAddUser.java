package com.atey.entity;

import lombok.Data;

import java.util.List;

@Data
public class ChartAddUser {
    private List<String> data;
    private List<Integer> value;
}
