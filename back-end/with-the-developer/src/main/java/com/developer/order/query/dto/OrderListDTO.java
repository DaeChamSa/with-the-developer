package com.developer.order.query.dto;

import com.developer.order.command.domain.aggregate.OrderStatus;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderListDTO {
    private Long orderCode;         // 주문번호
    private LocalDateTime orderDate;        // 주문날짜
    private OrderStatus orderStatus;        // 주문상태
    private Long paymentCode;    // 결제 코드
    private List<OrderGoodsListDTO> orderGoods;     // 주문한 굿즈들
}
