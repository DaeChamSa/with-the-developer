package com.developer.order.command.domain.repository;

import com.developer.order.command.domain.aggregate.Order;

import java.util.Optional;

public interface OrderRepository {

    // 주문 저장
    Order save(Order order);

    // 주문 고유 번호로 주문 이력 찾기
    Optional<Order> findByOrderUid(String OrderUid);

    // 주문 삭제
    void delete(Order order);

    // 결제코드로 주문 찾기
    Optional<Order> findByPaymentCode(Long paymentCode);

    // 주문코드와 사용자코드로 주문내역 찾기
    Optional<Order> findByUserCodeAndOrderCode(Long userCode, Long orderCode);
}
