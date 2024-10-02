package com.developer.cartGoods.command.application.service;

import com.developer.cartGoods.command.application.dto.CartGoodsAddDTO;
import com.developer.cartGoods.command.domain.aggregate.CartGoods;
import com.developer.cartGoods.command.domain.aggregate.CartGoodsCompositeKey;
import com.developer.cartGoods.command.infrastructure.repository.CartGoodsRepository;
import com.developer.goods.command.domain.Goods;
import com.developer.goods.command.infrastructure.repository.JpaGoodsRepository;
import com.developer.user.command.domain.aggregate.User;
import com.developer.user.command.domain.repository.UserRepository;
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
    private CartGoodsRepository cartGoodsRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JpaGoodsRepository jpaGoodsRepository;


    @Test
    @DisplayName("장바구니 굿즈 추가 테스트")
    void addCartGoodsTest() {

        String userId = "user03"; // 테스트용 사용자 ID

        //given
        CartGoodsAddDTO cartGoodsAddDTO = new CartGoodsAddDTO();
        cartGoodsAddDTO.setGoodsCode((long) 5);
        cartGoodsAddDTO.setGoodsAmount(1);

        //when
        cartGoodsService.addCart(cartGoodsAddDTO, userId);

        //then
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Goods goods = jpaGoodsRepository.findById(cartGoodsAddDTO.getGoodsCode())
                .orElseThrow(() -> new RuntimeException("Goods not found"));

        boolean existsCartGoods = cartGoodsRepository.existsByUserAndGoods(user, goods);

        // 장바구니에 추가된 상품이 있는지 확인
        Assertions.assertTrue(existsCartGoods, "장바구니에 상품이 추가되지 않았습니다.");

    }

    @Test
    @DisplayName("장바구니 굿즈 삭제 테스트")
    void deleteCartGoodsTest() {

        String userId = "user03"; // 테스트용 사용자 ID
        Long goodsCode = 5L;

        // Given: 사용자가 장바구니에 상품을 가지고 있다고 가정
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Goods goods = jpaGoodsRepository.findById(goodsCode)
                .orElseThrow(() -> new RuntimeException("Goods not found"));

        CartGoods cartGoods = new CartGoods(new CartGoodsCompositeKey(user.getUserCode(), goods.getGoodsCode()), user, goods, 1);
        cartGoodsRepository.save(cartGoods);  // 장바구니에 굿즈 추가

        // When: 장바구니에서 굿즈 삭제
        cartGoodsService.deleteGoods(goodsCode, userId);

        // Then: 장바구니에 해당 굿즈가 더 이상 존재하지 않는지 확인
        boolean existsCartGoods = cartGoodsRepository.existsByUserAndGoods(user, goods);

        Assertions.assertFalse(existsCartGoods, "굿즈가 삭제되지 않았습니다.");
    }

}