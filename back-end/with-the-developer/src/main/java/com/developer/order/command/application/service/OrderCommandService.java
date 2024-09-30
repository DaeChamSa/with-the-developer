package com.developer.order.command.application.service;

import com.developer.order.command.application.dto.OrderGoodsDTO;
import com.developer.order.command.domain.aggregate.Order;
import com.developer.order.command.domain.aggregate.OrderGoods;
import com.developer.order.command.domain.repository.OrderGoodsRepository;
import com.developer.order.command.domain.repository.OrderRepository;
import com.developer.payment.command.domain.aggregate.Payment;
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
    private final OrderGoodsRepository orderGoodsRepository;

    @Transactional
    public String createOrder(Long userCode, List<OrderGoodsDTO> orderGoodsDTOS) {
        List<OrderGoods> orderGoodsList = new ArrayList<>();
        log.info("createOrder 호출");
        for (OrderGoodsDTO orderGoodsDTO : orderGoodsDTOS) {
            orderGoodsList.add(OrderGoods.createOrderGoods(orderGoodsDTO.getGoodsCode(), orderGoodsDTO.getGoodsAmount(), 1000));
        }

        Payment payment = new Payment();

        Order order = Order.createOrder(userCode, payment.getPaymentCode(), orderGoodsList);

        Order save = orderRepository.save(order);

        log.info("orderCode {}", save.getOrderCode());

        for(OrderGoods orderGoods : orderGoodsList){
            orderGoods.setOrder(save);
            orderGoodsRepository.save(orderGoods);
        }

        return save.getOrderUid();
    }
}
