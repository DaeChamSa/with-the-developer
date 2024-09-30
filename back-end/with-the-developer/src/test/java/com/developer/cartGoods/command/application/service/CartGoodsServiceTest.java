package com.developer.cartGoods.command.application.service;

import com.developer.cartGoods.command.application.dto.CartGoodsAddDTO;
import com.developer.cartGoods.command.infrastructure.repository.CartGoodsRepository;
import com.developer.cartGoods.query.mapper.CartGoodsMapper;
import com.developer.goods.command.domain.Goods;
import com.developer.goods.command.infrastructure.repository.GoodsRepository;
import com.developer.user.command.entity.User;
import com.developer.user.command.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class CartGoodsServiceTest {

    @Autowired
    private CartGoodsService cartGoodsService;

    @Autowired
    private CartGoodsMapper cartGoodsMapper;

    @Autowired
    private CartGoodsRepository cartGoodsRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GoodsRepository goodsRepository;

    @DisplayName("장바구니 굿즈 추가 테스트")
    @Test
    void addCartGoodsTest(){
        //given
        CartGoodsAddDTO cartGoodsAddDTO = new CartGoodsAddDTO();
        cartGoodsAddDTO.setGoodsCode((long)5);
        cartGoodsAddDTO.setGoodsAmount(1);

        String userId = "user03"; // 테스트용 사용자 ID

        //when
        cartGoodsService.addCart(cartGoodsAddDTO, userId);

        //then
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Goods goods = goodsRepository.findById(cartGoodsAddDTO.getGoodsCode())
                .orElseThrow(() -> new RuntimeException("Goods not found"));

        boolean existsCartGoods = cartGoodsRepository.existsByUserAndGoods(user, goods);

        // 장바구니에 추가된 상품이 있는지 확인
        Assertions.assertTrue(existsCartGoods, "장바구니에 상품이 추가되지 않았습니다.");

    }


}