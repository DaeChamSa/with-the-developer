package com.developer.orderservice.order.command.application.controller;

import com.developer.orderservice.client.UserServiceClient;
import com.developer.orderservice.common.success.SuccessCode;
import com.developer.orderservice.order.command.application.dto.RequestOrderGoodsDTO;
import com.developer.orderservice.order.command.application.service.OrderCommandService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "order", description = "주문 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderCommandController {

    private final OrderCommandService orderCommandService;
    private final UserServiceClient userServiceClient;

    // 주문 생성
    @PostMapping
    @Operation(summary = "주문 생성", description = "새로운 주문을 생성합니다.")
    public ResponseEntity<String> createOrder(@RequestBody RequestOrderGoodsDTO orderGoods) {

        Long currentUserCode = userServiceClient.getCurrentUserCode();

        String orderUid = orderCommandService.createOrder(currentUserCode, orderGoods.getOrderGoods());

        return ResponseEntity.ok(orderUid);
    }

    // 주문 취소
    @PostMapping("/{orderCode}")
    @Operation(summary = "주문 취소", description = "완료된 주문을 취소합니다.")
    public ResponseEntity<SuccessCode> orderCancel(@PathVariable(name = "orderCode") Long orderCode){

        Long currentUserCode = userServiceClient.getCurrentUserCode();
        orderCommandService.orderCancel(currentUserCode, orderCode);

        return ResponseEntity.ok(SuccessCode.ORDER_CANCEL_OK);
    }

}
