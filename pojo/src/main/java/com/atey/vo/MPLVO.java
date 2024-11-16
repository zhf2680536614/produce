package com.atey.vo;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Data
public class MPLVO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private List<MarketProducesPlusVO> list;
    private Long date;
}
