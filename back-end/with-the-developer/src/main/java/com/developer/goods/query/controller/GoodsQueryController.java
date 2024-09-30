package com.developer.goods.query.controller;

import com.developer.goods.query.dto.GoodsResponseDTO;
import com.developer.goods.query.service.GoodsQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/goods")
public class GoodsQueryController {

    private final GoodsQueryService goodsQueryService;

    // 굿즈 전체 조회
    @GetMapping("/goodslist")
    public ResponseEntity<List<GoodsResponseDTO>> selectAllGoods(@RequestParam(defaultValue = "1") Integer page) {
        List<GoodsResponseDTO> goodsList = goodsQueryService.selectAllGoods(page);

        return ResponseEntity.ok(goodsList);
    }

    // 굿즈 특정 번호로 조회
    @GetMapping("/goodslist/{goodsCode}")
    public ResponseEntity<GoodsResponseDTO> selectGoodsByCode(@PathVariable Long goodsCode) {
        GoodsResponseDTO goodsResponseDTO = goodsQueryService.selectGoodsByCode(goodsCode);

        return ResponseEntity.ok(goodsResponseDTO);
    }
}
