package com.developer.cartGoods.command.application.controller;

import com.developer.cartGoods.command.application.dto.CartGoodsAddDTO;
import com.developer.cartGoods.command.application.service.CartGoodsService;
import com.developer.user.security.SecurityUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "cart-goods", description = "장바구니 API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/cart-goods")
public class CartGoodsController {

    private final CartGoodsService cartGoodsService;

    // 장바구니 상품 추가
    @PostMapping(value = "/regist")
    @Operation(summary = "장바구니 상품 추가", description = "장바구니에 새로운 상품을 추가합니다.")
    public ResponseEntity<Void> addCartGoods(
            @RequestPart(value="cartGoodsAddDTO") CartGoodsAddDTO cartGoodsAddDTO) {

        String userId = SecurityUtil.getCurrentUserId();

        cartGoodsService.addCart(cartGoodsAddDTO, userId);

        return ResponseEntity.ok().build();
    }

    //장바구니 상품 삭제
    @DeleteMapping("/delete/{goodsCode}")
    @Operation(summary = "장바구니 상품 삭제", description = "장바구니에 담겨 있는 상품을 삭제합니다.")
    public ResponseEntity<Void> deleteCartGoods(@PathVariable Long goodsCode) {
        String userId = SecurityUtil.getCurrentUserId();

        cartGoodsService.deleteGoods(goodsCode, userId);

        return ResponseEntity.ok().build();
    }

}
