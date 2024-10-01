package com.developer.payment.command.domain.aggregate;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Table(name = "payment")
@Entity
@NoArgsConstructor
public class Payments {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_code")
    private Long paymentCode;

    @Column(name = "payment_price")
    private int paymentPrice;

    @Column(name = "payment_status")
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    @Column(name = "payment_uid")
    private String paymentUid; // 결제 고유 번호

    @JoinColumn(name = "user_code")
    private Long userCode;

    @Builder
    public Payments(int paymentPrice, PaymentStatus status, String orderUid, Long userCode) {
        this.paymentPrice = paymentPrice;
        this.paymentStatus = status;
        this.paymentUid = orderUid;
        this.userCode = userCode;
    }

    // ===서비스 로직=== //
    // 결제 성공
    public void changePaymentBySuccess(PaymentStatus status, String paymentUid) {
        this.paymentStatus = status;
        this.paymentUid = paymentUid;
    }

    // 취소 성공
    public void changePaymentByFailure(PaymentStatus status) {
        this.paymentStatus = status;
    }



}
