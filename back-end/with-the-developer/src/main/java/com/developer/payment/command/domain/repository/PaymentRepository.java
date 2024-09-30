package com.developer.payment.command.domain.repository;

import com.developer.payment.command.domain.aggregate.Payments;

import java.util.Optional;

public interface PaymentRepository {
    Optional<Payments> findById(Long paymentCode);

    void deleteById(Long paymentCode);

    Payments save(Payments payment);

    Optional<Payments> findPaymentByUserCodeAndPaymentUid(Long userCode, String paymentUid);
}
