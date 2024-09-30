package com.developer.order.command.domain.repository;

import com.developer.order.command.domain.aggregate.Order;

import java.util.Optional;

public interface OrderRepository {
    Order save(Order order);

    Optional<Order> findByOrderUid(String OrderUid);

    void delete(Order order);

    Optional<Order> findByPaymentCode(Long paymentCode);
}
