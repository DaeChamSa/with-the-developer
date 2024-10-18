package com.developer.cartGoods.command.domain.aggregate;

import com.developer.goods.command.domain.Goods;
import com.developer.user.command.domain.aggregate.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class CartGoods {

    @EmbeddedId
    private CartGoodsCompositeKey cartGoodsCode;

    @ManyToOne
    @MapsId("userCode")
    @JoinColumn(name = "user_code")
    private User user;

    @ManyToOne
    @MapsId("goodsCode")
    @JoinColumn(name = "goods_code")
    private Goods goods;

    @Column(name = "goods_amount")
    private int goodsAmount;

    public CartGoods(CartGoodsCompositeKey cartGoodsCode, User user, Goods goods, int goodsAmount) {
        this.cartGoodsCode = cartGoodsCode;
        this.user = user;
        this.goods = goods;
        this.goodsAmount = goodsAmount;
    }


}