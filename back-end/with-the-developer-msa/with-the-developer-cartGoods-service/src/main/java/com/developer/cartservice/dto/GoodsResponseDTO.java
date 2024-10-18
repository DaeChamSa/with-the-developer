package com.developer.cartservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GoodsResponseDTO {
    private long goodsCode;
    private String goodsName;
    private String goodsContent;
    private String goodsStatus;
    private int goodsPrice;
}