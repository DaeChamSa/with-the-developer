package com.developer.payment.command.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.developer.payment.command.domain.aggregate.Payment;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PaymentRepository{
    Optional<Payment> findById(Long paymentCode);

    void deleteById(Long paymentCode);
}
