package com.developer.order.command.application.dto;

import lombok.Data;

@Data
public class OrderGoodsDTO {

    private Long goodsCode; // 상품코드
    private int goodsAmount;   // 상품 갯수
}
