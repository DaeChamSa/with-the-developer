package com.developer.order.command.application.service;

import com.developer.common.exception.CustomException;
import com.developer.common.exception.ErrorCode;
import com.developer.goods.command.domain.Goods;
import com.developer.goods.command.infrastructure.repository.JpaGoodsRepository;
import com.developer.order.command.application.dto.OrderGoodsDTO;
import com.developer.order.command.domain.aggregate.Order;
import com.developer.order.command.domain.aggregate.OrderGoods;
import com.developer.order.command.domain.aggregate.OrderStatus;
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
    private final JpaGoodsRepository goodsRepository;

    // 주문 생성
    @Transactional
    public String createOrder(Long userCode, List<OrderGoodsDTO> orderGoodsDTOS) {
        List<OrderGoods> orderGoodsList = new ArrayList<>();
        log.info("createOrder 호출");



        for (OrderGoodsDTO orderGoodsDTO : orderGoodsDTOS) {
            Goods goods = goodsRepository.findById(orderGoodsDTO.getGoodsCode())
                    .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_GOODS));

            OrderGoods orderGoods = OrderGoods.createOrderGoods(
                    orderGoodsDTO.getGoodsCode(), orderGoodsDTO.getGoodsAmount(), goods.getGoodsPrice()
            );

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

    // 해당 주문 취소
    @Transactional
    public void orderCancel(Long userCode, Long orderCode){

        // 사용자코드와 주문코드로 주문 찾기
        Order order = orderRepository.findByUserCodeAndOrderCode(userCode, orderCode)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ORDER));

        // 찾은 주문에서 결제코드로 결제 찾기
        Payments payments = paymentsRepository.findById(order.getPaymentCode())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_PAYMENT));

        // 결제가 완료된 상황이면 주문 취소 불가능
        if (payments.getPaymentStatus().equals(PaymentStatus.OK)){

            throw new CustomException(ErrorCode.PAYMENT_ALREADY_PAID);
        } else if (payments.getPaymentStatus().equals(PaymentStatus.CANCEL)
                || order.getOrderStatus().equals(OrderStatus.CANCEL)) {

            // 이미 취소된 상품
            throw new CustomException(ErrorCode.PAYMENT_ALREADY_CANCEL);
        }

        order.changeOrderByFailure(OrderStatus.CANCEL);
    }
}
