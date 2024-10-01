package com.developer.order.query.controller;

import com.developer.order.query.dto.OrderListDTO;
import com.developer.order.query.dto.ResponseOrderListDTO;
import com.developer.order.query.service.OrderQueryService;
import com.developer.user.security.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderQueryController {

    private final OrderQueryService orderQueryService;

    // 주문 내역 조회
    @GetMapping("/list")
    public ResponseEntity<List<ResponseOrderListDTO>> orderList(){
        Long currentUserCode = SecurityUtil.getCurrentUserCode();

        return ResponseEntity.ok(orderQueryService.orderList(currentUserCode));
    }
}
