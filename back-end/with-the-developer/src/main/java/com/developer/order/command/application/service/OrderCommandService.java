package com.developer.order.command.application.service;

import com.developer.order.command.application.dto.OrderGoodsDTO;
import com.developer.order.command.domain.aggregate.Order;
import com.developer.order.command.domain.aggregate.OrderGoods;
import com.developer.order.command.domain.repository.OrderRepository;
import com.developer.payment.command.domain.aggregate.PaymentStatus;
import com.developer.payment.command.domain.aggregate.Payments;
import com.developer.payment.command.domain.repository.PaymentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderCommandService {

    private final OrderRepository orderRepository;
    private final PaymentRepository paymentsRepository;

    @Transactional
    public String createOrder(Long userCode, List<OrderGoodsDTO> orderGoodsDTOS) {
        List<OrderGoods> orderGoodsList = new ArrayList<>();
        log.info("createOrder 호출");
        for (OrderGoodsDTO orderGoodsDTO : orderGoodsDTOS) {
            OrderGoods orderGoods = OrderGoods.createOrderGoods(orderGoodsDTO.getGoodsCode(), orderGoodsDTO.getGoodsAmount(), 1000);
            log.info("orderGoods getOrderPrice {}", orderGoods.getOrderPrice());

            orderGoodsList.add(orderGoods);
        }

        Order order = Order.createOrder(userCode, orderGoodsList);

        Payments payment = new Payments(
                order.getTotalPrice(), PaymentStatus.READY, order.getOrderUid(), userCode
        );

        Payments savePayment = paymentsRepository.save(payment);

        order.setPaymentCode(savePayment.getPaymentCode());
        // Order 객체 저장
        Order savedOrder = orderRepository.save(order);
        log.info("orderCode {}", savedOrder.getOrderCode());

        return savedOrder.getOrderUid();
    }
}
