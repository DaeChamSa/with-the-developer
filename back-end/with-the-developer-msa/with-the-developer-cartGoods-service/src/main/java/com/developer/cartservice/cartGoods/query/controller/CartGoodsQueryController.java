package com.developer.cartservice.cartGoods.query.controller;

import com.developer.cartservice.cartGoods.query.dto.CartGoodsQueryDTO;
import com.developer.cartservice.cartGoods.query.service.CartGoodsQueryService;
import com.developer.cartservice.client.UserServiceClient;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "cart-goods", description = "장바구니 API")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/cart-goods")
public class CartGoodsQueryController {

    private final CartGoodsQueryService cartGoodsQueryService;
    private final UserServiceClient userServiceClient;

    @GetMapping
    @Operation(summary = "장바구니 상품 리스트 조회", description = "장바구니에 담긴 상품 리스트를 조회합니다.")
    public ResponseEntity<List<CartGoodsQueryDTO>> selectCartGoodsList(@RequestParam(name = "page", defaultValue = "1")Integer page){

        String userId = userServiceClient.getCurrentUserId();

        List<CartGoodsQueryDTO> goodsList = cartGoodsQueryService.selectCartGoodsList(page, userId);

        return ResponseEntity.ok(goodsList);
    }
}
