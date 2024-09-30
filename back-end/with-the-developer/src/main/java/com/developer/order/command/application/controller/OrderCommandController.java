package com.developer.order.command.application.controller;

import com.developer.order.command.application.dto.OrderGoodsDTO;
import com.developer.order.command.application.dto.RequestOrderGoodsDTO;
import com.developer.order.command.application.service.OrderCommandService;
import com.developer.user.security.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderCommandController {

    private final OrderCommandService orderCommandService;

    @PostMapping("/order/create")
    public ResponseEntity<String> createOrder(@RequestBody RequestOrderGoodsDTO orderGoods) {

        Long currentUserCode = SecurityUtil.getCurrentUserCode();

        String orderUid = orderCommandService.createOrder(currentUserCode, orderGoods.getOrderGoods());

        return ResponseEntity.ok(orderUid);
    }
}
