package com.developer.cartGoods.command.domain.aggregate;

import com.developer.user.command.entity.User;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class CartGoods{

    @EmbeddedId
    private CartGoodsCompositeKey cartGoodsCode;

    @ManyToOne
    @MapsId("user_code")
    @JoinColumn(name="userCode")
    private User user;

//    @ManyToOne
//    @MapsId("goods_code")
//    @JoinColumn(goodsCode)
//    private Goods goods;

}