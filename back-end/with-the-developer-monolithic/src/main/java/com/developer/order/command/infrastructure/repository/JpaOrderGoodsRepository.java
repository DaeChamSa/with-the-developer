package com.developer.order.command.infrastructure.repository;

import com.developer.order.command.domain.aggregate.OrderGoods;
import com.developer.order.command.domain.repository.OrderGoodsRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaOrderGoodsRepository extends OrderGoodsRepository, JpaRepository<OrderGoods, Long> {

}
