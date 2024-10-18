package com.developer.orderservice.payment.query.dto;

import com.developer.orderservice.payment.command.domain.aggregate.PaymentStatus;
import lombok.Data;

@Data
public class ResponsePaymentDTO {
    private Long paymentCode;
    private int paymentPrice;
    private PaymentStatus paymentStatus;
    private String paymentUid;  // 주문 고유 코드
    private Long userCode;
}