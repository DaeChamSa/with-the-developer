package com.developer.payment.command.domain.aggregate;

import com.developer.payment.command.domain.aggregate.PaymentStatus;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Table(name = "payment")
@Entity
@NoArgsConstructor
public class Payment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_code")
    private Long paymentCode;

    @Column(name = "payment_price")
    private int paymentPrice;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    @Column(name = "payment_uid")
    private String paymentUid; // 결제 고유 번호

    @Builder
    public Payment(int price, PaymentStatus status) {
        this.paymentPrice = price;
        this.paymentStatus = status;
    }

    public void changePaymentBySuccess(PaymentStatus status, String paymentUid) {
        this.paymentStatus = status;
        this.paymentUid = paymentUid;
    }
}
