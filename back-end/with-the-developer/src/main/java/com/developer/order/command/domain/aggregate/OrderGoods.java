package com.developer.order.command.domain.aggregate;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Table(name = "order_goods")
@Entity
@NoArgsConstructor
public class OrderGoods {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_goods_code", nullable = false)
    private Long orderGoodsCode;

    @Column(name = "goods_amount", nullable = false)
    private int goodsAmount;

    @Column(name = "order_price", nullable = false)
    private int orderPrice;

    @JoinColumn(name = "goods_code")
    private Long goodsCode;     // GoodsCode

    // 주문번호 넣기
    @Setter
    @JoinColumn(name = "order_code")
    @ManyToOne(fetch = FetchType.LAZY)
    private Order order;         // orderCode

    private OrderGoods(Long goodsCode, int goodsAmount, int orderPrice) {
        this.goodsCode = goodsCode;
        this.goodsAmount = goodsAmount;
        this.orderPrice = orderPrice;
    }

    // === 서비스 로직 === //
    // 오더 굿즈 생성
    public static OrderGoods createOrderGoods(Long goodsCode, int goodsAmount, int orderPrice) {

        return new OrderGoods(goodsCode, goodsAmount, orderPrice);
    }


    // 총 금액 반환
    public int getTotalPrice() {
        return getOrderPrice() * getGoodsAmount();
    }

}
