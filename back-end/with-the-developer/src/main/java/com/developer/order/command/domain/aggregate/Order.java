package com.developer.order.command.domain.aggregate;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Table(name = "orders")
@Entity
@NoArgsConstructor
public class Order {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_code")
    private Long orderCode;

    @Column(name = "order_status", nullable = false, length = 10)
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;         // 주문상태

    @Column(name = "order_create_date", nullable = false)
    private LocalDateTime orderCreateDate;  // 주문 등록 날짜

    @Column(name = "order_cancel_date", nullable = false)
    private LocalDateTime orderCancelDate;  // 주문 취소 날짜

    @Column(name = "order_uid", nullable = false, unique = true)
    private String orderUid;        // 주문 고유번호
    
    @JoinColumn(name = "user_code")
    private Long userCode;      // 사용자 코드

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderGoods> orderGoods = new ArrayList<>();        // 오더굿즈

    @Setter
    @JoinColumn(name = "payment_code")
    private Long paymentCode;       // 결제 코드

    @Builder
    public Order(OrderStatus orderStatus,
                 LocalDateTime orderCreateDate,
                 LocalDateTime orderCancelDate,
                 String orderUid, Long userCode,
                 List<OrderGoods> orderGoods, Long paymentCode) {
        this.orderStatus = orderStatus;
        this.orderCreateDate = orderCreateDate;
        this.orderCancelDate = orderCancelDate;
        this.orderUid = orderUid;
        this.userCode = userCode;
        this.orderGoods = orderGoods;
        this.paymentCode = paymentCode;
    }

    private Order(Long userCode, List<OrderGoods> orderGoods) {
        this.userCode = userCode;                   // 사용자 코드
        this.orderGoods = orderGoods;               // 주문굿즈들
        this.orderCreateDate = LocalDateTime.now(); // 주문 날짜
        this.orderStatus = OrderStatus.READY;       // 주문 상태
        this.orderUid = UUID.randomUUID().toString();      // 주문 고유코드
    }

    // === 서비스 로직 === //

    public void addOrderGoods(OrderGoods orderGoodss) {
        orderGoods.add(orderGoodss);
        orderGoodss.setOrder(this);
    }
    // Order 생성 메서드
    public static Order createOrder(Long userCode, List<OrderGoods> orderGoods) {
        Order order = new Order(userCode, orderGoods);

        for (OrderGoods orderGood : orderGoods) {
            orderGood.setOrder(order);
        }

        return order;
    }

    // 주문 상품 총 금액 반환
    public int getTotalPrice() {
        int totalPrice = 0;
        for (OrderGoods orderGood : orderGoods) {
            totalPrice += orderGood.getTotalPrice();
        }
        return totalPrice;
    }

    // setOrderGoods
    public void setOrderGoods(OrderGoods orderGoods){
        this.orderGoods.add(orderGoods);
    }

    // 주문 상태 취소로 변경
    public void changeOrderByFailure(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

}
