package com.developer.orderservice.order.command.infrastructure.repository;

import com.developer.orderservice.order.command.domain.aggregate.Order;
import com.developer.orderservice.order.command.domain.repository.OrderRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaOrderRepository extends OrderRepository, JpaRepository<Order, Long> {

}
