package com.developer.orderservice.order.query.controller;

import com.developer.orderservice.client.UserServiceClient;
import com.developer.orderservice.order.query.dto.ResponseOrderListDTO;
import com.developer.orderservice.order.query.service.OrderQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "order", description = "주문 API")
@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderQueryController {

    private final OrderQueryService orderQueryService;
    private final UserServiceClient userServiceClient;

    // 주문 내역 조회
    @GetMapping
    @Operation(summary = "주문 내역 조회", description = "주문한 굿즈 내역을 조회합니다.")
    public ResponseEntity<List<ResponseOrderListDTO>> orderList(){
        Long currentUserCode = userServiceClient.getCurrentUserCode();

        return ResponseEntity.ok(orderQueryService.orderList(currentUserCode));
    }
}
