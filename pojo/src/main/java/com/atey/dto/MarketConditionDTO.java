package com.atey.dto;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class MarketConditionDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;


    private String name;
    private String username;
    private Integer category;
    private Short order;
    private Short orderKind;
}
