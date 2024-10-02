package com.developer.cartGoods.query.service;

import com.developer.cartGoods.command.application.dto.CartGoodsAddDTO;
import com.developer.cartGoods.command.application.service.CartGoodsService;
import com.developer.cartGoods.query.dto.CartGoodsQueryDTO;
import com.developer.cartGoods.query.mapper.CartGoodsMapper;
import com.developer.common.exception.CustomException;
import com.developer.common.exception.ErrorCode;
import com.developer.goods.command.domain.Goods;
import com.developer.goods.command.infrastructure.repository.JpaGoodsRepository;
import com.developer.user.command.domain.aggregate.User;
import com.developer.user.command.domain.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class CartGoodsQueryServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JpaGoodsRepository jpaGoodsRepository;

    @Autowired
    private CartGoodsService cartGoodsService;

    @Autowired
    private CartGoodsMapper cartGoodsMapper;


    @Test
    @DisplayName("장바구니 조회 테스트")
    void getCartGoods() {
        //given
        String userId = "user02"; //존재하는 유저로 기입 필수

        //장바구니 조회 위해 장바구니에 굿즈 추가
        CartGoodsAddDTO cartGoodsAddDTO = new CartGoodsAddDTO();
        cartGoodsAddDTO.setGoodsCode((long) 3); // 존재하는 굿즈로 필수 기입
        cartGoodsAddDTO.setGoodsAmount(1);

        cartGoodsService.addCart(cartGoodsAddDTO, userId);

        //사용자 존재 여부 확인
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));

        Goods goods = jpaGoodsRepository.findById(cartGoodsAddDTO.getGoodsCode())
                .orElseThrow(()->new CustomException(ErrorCode.NOT_FOUND_POST));

        //when
        List<CartGoodsQueryDTO> cartGoodsList = cartGoodsMapper.selectCartGoodsList(user.getUserCode());

        //then
        assertNotNull(cartGoodsList);
    }

}