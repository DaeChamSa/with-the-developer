package com.developer.cartGoods.query.controller;

import com.developer.cartGoods.query.dto.CartGoodsQueryDTO;
import com.developer.cartGoods.query.service.CartGoodsQueryService;
import com.developer.user.security.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/cart-goods")
public class CartGoodsQueryController {

    private final CartGoodsQueryService cartGoodsQueryService;

    @GetMapping("/goodslist")
    public ResponseEntity<List<CartGoodsQueryDTO>> selectCartGoodsList(@RequestParam(defaultValue = "1")Integer page){

        String userId = SecurityUtil.getCurrentUserId();

        List<CartGoodsQueryDTO> goodsList = cartGoodsQueryService.selectCartGoodsList(page, userId);

        return ResponseEntity.ok(goodsList);
    }
}
