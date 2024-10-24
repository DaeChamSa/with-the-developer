package com.developer.goods.query.controller;

import com.developer.goods.query.dto.GoodsResponseDTO;
import com.developer.goods.query.service.GoodsQueryService;
import com.developer.search.query.dto.SearchGoodsDTO;
import com.developer.search.query.service.SearchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "goods", description = "굿즈 API")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/public/goods")
public class GoodsQueryController {

    private final GoodsQueryService goodsQueryService;
    private final SearchService searchService;

    // 굿즈 전체 조회
    @GetMapping
    @Operation(summary = "굿즈 목록 조회", description = "등록되어 있는 굿즈 목록을 조회합니다.")
    public ResponseEntity<List<GoodsResponseDTO>> selectAllGoods(@RequestParam(name = "page", defaultValue = "1") Integer page) {
        List<GoodsResponseDTO> goodsList = goodsQueryService.selectAllGoods(page);

        return ResponseEntity.ok(goodsList);
    }

    // 굿즈 특정 번호로 조회
    @GetMapping("/{goodsCode}")
    @Operation(summary = "굿즈 상세 내용 조회", description = "등록된 굿즈의 상세 내용을 조회합니다.")
    public ResponseEntity<GoodsResponseDTO> selectGoodsByCode(@PathVariable Long goodsCode) {
        GoodsResponseDTO goodsResponseDTO = goodsQueryService.selectGoodsByCode(goodsCode);

        return ResponseEntity.ok(goodsResponseDTO);
    }

    // 굿즈 검색하기
    @GetMapping("/search")
    @Operation(summary = "굿즈 검색", description = "키워드(keyword)를 포함하고 있는 굿즈를 검색을 통해 조회합니다.")
    public ResponseEntity<List<SearchGoodsDTO>> searchGoods(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "1") Integer page)
    {
        List<SearchGoodsDTO> searchGoodsResultDTO = searchService.searchGoods(keyword, page);
        return ResponseEntity.ok(searchGoodsResultDTO);
    }
}
