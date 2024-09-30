package com.developer.cartGoods.command.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartGoodsAddDTO {
    private Long goodsCode;
    private int goodsAmount;

}
