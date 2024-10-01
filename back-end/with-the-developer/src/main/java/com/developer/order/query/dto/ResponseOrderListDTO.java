package com.developer.order.query.dto;

import com.developer.order.command.domain.aggregate.OrderStatus;
import com.developer.payment.query.dto.ResponsePaymentDTO;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ResponseOrderListDTO {
    private Long orderCode;         // 주문번호
    private LocalDateTime orderDate;        // 주문날짜
    private OrderStatus orderStatus;        // 주문상태
    private ResponsePaymentDTO responsePaymentDTO;    // 결제 코드
    private List<OrderGoodsListDTO> orderGoods;     // 주문한 굿즈들

    // 생성자 추가
    public ResponseOrderListDTO(Long orderCode, LocalDateTime orderDate,
                                OrderStatus orderStatus, ResponsePaymentDTO responsePaymentDTO,
                                List<OrderGoodsListDTO> orderGoods) {
        this.orderCode = orderCode;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.responsePaymentDTO = responsePaymentDTO;
        this.orderGoods = orderGoods;
    }
}
