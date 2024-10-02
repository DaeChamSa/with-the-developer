package com.developer.goods.command.application.service;

import com.developer.goods.command.application.dto.GoodsCreateDTO;
import com.developer.goods.command.application.dto.GoodsUpdateDTO;
import com.developer.goods.command.domain.Goods;
import com.developer.goods.command.infrastructure.repository.JpaGoodsRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class GoodsServiceTest {

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private JpaGoodsRepository GoodsRepository;

    @Test
    @DisplayName("굿즈 등록 테스트")
    void registGoods(){
        //given
        GoodsCreateDTO goodsCreateDTO = new GoodsCreateDTO();
        goodsCreateDTO.setGoodsName("상품1");
        goodsCreateDTO.setGoodsContent("상품 1 설명");
        goodsCreateDTO.setGoodsStatus("판매중");
        goodsCreateDTO.setGoodsPrice(1000);

        //when
        goodsService.createGoods(goodsCreateDTO);

        //then
        Goods registGoods = GoodsRepository.findByGoodsName("상품1");

        assertNotNull(registGoods);
    }

    @Test
    @DisplayName("굿즈 수정 테스트")
    void updateGoods(){
        // 수정테스트 위해 상품 먼저 등록
        //given
        GoodsCreateDTO goodsCreateDTO = new GoodsCreateDTO();
        goodsCreateDTO.setGoodsName("상품1");
        goodsCreateDTO.setGoodsContent("상품 1 설명");
        goodsCreateDTO.setGoodsStatus("판매중");
        goodsCreateDTO.setGoodsPrice(1000);

        Long registGoods = goodsService.createGoods(goodsCreateDTO);

        //when
        GoodsUpdateDTO goodsUpdateDTO = new GoodsUpdateDTO();
        goodsUpdateDTO.setGoodsCode(registGoods);
        goodsUpdateDTO.setGoodsName("수정된 상품1");
        goodsUpdateDTO.setGoodsContent("수정된 상품 1 설명");
        goodsUpdateDTO.setGoodsStatus("품절");
        goodsUpdateDTO.setGoodsPrice(1200);

        goodsService.updateGoods(goodsUpdateDTO);

        //then
        Goods updateGoods = GoodsRepository.findByGoodsName("수정된 상품1");

        assertNotNull(updateGoods);
    }

    @Test
    @DisplayName("굿즈 삭제 테스트")
    void deleteGoods(){
        //삭제 테스트 위해 먼저 굿즈 등록
        //given
        GoodsCreateDTO goodsCreateDTO = new GoodsCreateDTO();
        goodsCreateDTO.setGoodsName("상품1");
        goodsCreateDTO.setGoodsContent("상품 1 설명");
        goodsCreateDTO.setGoodsStatus("판매중");
        goodsCreateDTO.setGoodsPrice(1000);

        Long registGoods = goodsService.createGoods(goodsCreateDTO);

        //when
        goodsService.deleteGoods(registGoods);

        // then
        assertFalse(GoodsRepository.findById(registGoods).isPresent());
    }
}