package com.developer.goods.command.application.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GoodsCreateDTO {
    private String goodsName;
    private String goodsContent;
    private String goodsStatus;
    private int goodsPrice;
}
