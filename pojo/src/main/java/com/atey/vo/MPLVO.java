package com.atey.vo;

import lombok.Data;

import java.util.List;

@Data
public class MPLVO {
    private List<MarketProducesPlusVO> list;
    private Long date;
}
