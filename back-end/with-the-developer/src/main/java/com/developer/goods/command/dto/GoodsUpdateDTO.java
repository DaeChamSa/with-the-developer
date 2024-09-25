package com.developer.goods.command.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GoodsUpdateDTO {
    private long goodsCode;
    private String goodsName;
    private String goodsContent;
    private int goodsStock;
    private String goodsStatus;
    private int goodsPrice;
}
