package com.developer.cartservice.cartGoods.command.domain.aggregate;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@IdClass(CartGoodsCompositeKey.class)
public class CartGoods {

    @Id
    private Long userCode;

    @Id
    private Long goodsCode;

    private int goodsAmount;

    public CartGoods(Long userCode, Long goodsCode, int goodsAmount) {
        this.userCode = userCode;
        this.goodsCode = goodsCode;
        this.goodsAmount = goodsAmount;
    }
}