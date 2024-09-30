package com.developer.order.command.infrastructure.repository;

import com.developer.order.command.domain.aggregate.Order;
import com.developer.order.command.domain.repository.OrderRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaOrderRepository extends OrderRepository, JpaRepository<Order, Long> {

}
