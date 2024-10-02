package com.developer.goods.query.service;

import com.developer.goods.command.application.dto.GoodsCreateDTO;
import com.developer.goods.command.application.service.GoodsService;
import com.developer.goods.command.infrastructure.repository.JpaGoodsRepository;
import com.developer.goods.query.dto.GoodsResponseDTO;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Transactional
class GoodsQueryServiceTest {

    @Autowired
    private GoodsQueryService goodsQueryService;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private JpaGoodsRepository goodsRepository;

    @Test
    @DisplayName("굿즈 조회 테스트")
    void selectAllGoods(){
        //given
        //굿즈 조회 테스트 위한 굿즈 등록
        GoodsCreateDTO goodsCreateDTO = new GoodsCreateDTO();
        goodsCreateDTO.setGoodsName("상품1");
        goodsCreateDTO.setGoodsContent("상품 1 설명");
        goodsCreateDTO.setGoodsStatus("판매중");
        goodsCreateDTO.setGoodsPrice(1000);

        goodsService.createGoods(goodsCreateDTO);

        //when
        List<GoodsResponseDTO> allGoods = goodsQueryService.selectAllGoods(Integer.MAX_VALUE);

        //then
        assertNotNull(allGoods);
    }

    @Test
    @DisplayName("굿즈 코드별 조회 테스트")
    void selectGoods(){
        // given
        // 굿즈 조회 위한 굿즈 등록
        GoodsCreateDTO goodsCreateDTO = new GoodsCreateDTO();
        goodsCreateDTO.setGoodsName("상품1");
        goodsCreateDTO.setGoodsContent("상품 1 설명");
        goodsCreateDTO.setGoodsStatus("판매중");
        goodsCreateDTO.setGoodsPrice(1000);

        goodsService.createGoods(goodsCreateDTO);

        Long registGoods = goodsService.createGoods(goodsCreateDTO);

        //when
        goodsQueryService.selectGoodsByCode(registGoods);

        //then
        assertNotNull(goodsQueryService.selectGoodsByCode(registGoods));

        // 존재하지 않는 코드 조회 시 예외 처리
        Long nonExistGoods = registGoods+999;

        goodsQueryService.selectGoodsByCode(nonExistGoods);

        assertFalse(goodsRepository.findById(nonExistGoods).isPresent());
    }
}