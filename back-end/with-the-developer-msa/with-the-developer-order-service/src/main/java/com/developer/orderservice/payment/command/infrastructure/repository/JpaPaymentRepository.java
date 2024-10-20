package com.developer.orderservice.payment.command.infrastructure.repository;

import com.developer.orderservice.payment.command.domain.aggregate.Payments;
import com.developer.orderservice.payment.command.domain.repository.PaymentRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaPaymentRepository extends PaymentRepository, JpaRepository<Payments, Long> {


}
