package com.developer.payment.command.infrastructure.repository;

import com.developer.payment.command.domain.aggregate.Payment;
import com.developer.payment.command.domain.repository.PaymentRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaPaymentRepository extends PaymentRepository, JpaRepository<Payment, Long> {


}
