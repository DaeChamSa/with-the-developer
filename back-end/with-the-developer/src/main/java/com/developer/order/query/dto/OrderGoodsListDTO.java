package com.developer.order.query.dto;

import lombok.Data;

@Data
public class OrderGoodsListDTO {

    private Long orderGoodsCode;    // 주문굿즈 생성 코드
    private Long goodsCode;         // 굿즈 코드
    private int orderGoodsPrice;    // 주문 금액
    private int orderGoodsAmount; // 갯수
    private String goodsName;       // 굿즈 이름
}
