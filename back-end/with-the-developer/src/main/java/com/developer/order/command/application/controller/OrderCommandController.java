package com.developer.order.command.application.controller;

import com.developer.common.success.SuccessCode;
import com.developer.order.command.application.dto.RequestOrderGoodsDTO;
import com.developer.order.command.application.service.OrderCommandService;
import com.developer.user.security.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderCommandController {

    private final OrderCommandService orderCommandService;

    // 주문 생성
    @PostMapping("/create")
    public ResponseEntity<String> createOrder(@RequestBody RequestOrderGoodsDTO orderGoods) {

        Long currentUserCode = SecurityUtil.getCurrentUserCode();

        String orderUid = orderCommandService.createOrder(currentUserCode, orderGoods.getOrderGoods());

        return ResponseEntity.ok(orderUid);
    }

    // 주문 취소
    @PostMapping("/cancel/{orderCode}")
    public ResponseEntity<SuccessCode> orderCancel(@PathVariable(name = "orderCode") Long orderCode){

        Long currentUserCode = SecurityUtil.getCurrentUserCode();
        orderCommandService.orderCancel(currentUserCode, orderCode);

        return ResponseEntity.ok(SuccessCode.ORDER_CANCEL_OK);
    }

}
