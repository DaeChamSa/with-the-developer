package com.developer.goods.query.dto;

import lombok.Data;

@Data
public class GoodsResponseDTO {
    private long goodsCode;
    private String goodsName;
    private String goodsContent;
    private String goodsStatus;
    private int goodsPrice;
}
