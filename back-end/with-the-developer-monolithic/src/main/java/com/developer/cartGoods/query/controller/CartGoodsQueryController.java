package com.developer.cartGoods.query.controller;

import com.developer.cartGoods.query.dto.CartGoodsQueryDTO;
import com.developer.cartGoods.query.service.CartGoodsQueryService;
import com.developer.user.security.SecurityUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "cart-goods", description = "장바구니 API")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/cart-goods")
public class CartGoodsQueryController {

    private final CartGoodsQueryService cartGoodsQueryService;

    @GetMapping
    @Operation(summary = "장바구니 상품 리스트 조회", description = "장바구니에 담긴 상품 리스트를 조회합니다.")
    public ResponseEntity<List<CartGoodsQueryDTO>> selectCartGoodsList(@RequestParam(name = "page", defaultValue = "1")Integer page){

        String userId = SecurityUtil.getCurrentUserId();

        List<CartGoodsQueryDTO> goodsList = cartGoodsQueryService.selectCartGoodsList(page, userId);

        return ResponseEntity.ok(goodsList);
    }
}
