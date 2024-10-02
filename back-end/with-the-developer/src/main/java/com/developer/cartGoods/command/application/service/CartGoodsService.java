package com.developer.cartGoods.command.application.service;

import com.developer.cartGoods.command.application.dto.CartGoodsAddDTO;
import com.developer.cartGoods.command.domain.aggregate.CartGoods;
import com.developer.cartGoods.command.domain.aggregate.CartGoodsCompositeKey;
import com.developer.cartGoods.command.infrastructure.repository.CartGoodsRepository;
import com.developer.common.exception.CustomException;
import com.developer.common.exception.ErrorCode;
import com.developer.goods.command.domain.Goods;
import com.developer.goods.command.infrastructure.repository.JpaGoodsRepository;
import com.developer.user.command.domain.aggregate.User;
import com.developer.user.command.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class CartGoodsService {

    private final CartGoodsRepository cartGoodsRepository;
    private final UserRepository userRepository;
    private final JpaGoodsRepository jpaGoodsRepository;

    // 장바구니 굿즈 추가
    @Transactional
    public void addCart(CartGoodsAddDTO cartGoodsAddDTO, String userId) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(()->new CustomException(ErrorCode.NOT_FOUND_USER));

        Goods goods = jpaGoodsRepository.findById(cartGoodsAddDTO.getGoodsCode())
                .orElseThrow(()->new CustomException(ErrorCode.NOT_FOUND_POST));

        // 중복 체크: 이미 해당 유저가 해당 상품을 장바구니에 추가했는지 확인
        boolean exists = cartGoodsRepository.existsByUserAndGoods(user, goods);
        if (exists) {
            throw new CustomException(ErrorCode.DUPLICATE_VALUE);  // 중복된 상품이 있으면 에러 처리
        }

        CartGoodsCompositeKey cartGoodsCompositeKey = new CartGoodsCompositeKey(user.getUserCode(), goods.getGoodsCode());

        CartGoods cartGoods = new CartGoods(cartGoodsCompositeKey, user, goods, cartGoodsAddDTO.getGoodsAmount());

        cartGoodsRepository.save(cartGoods);
    }

    // 장바구니 굿즈 삭제
    @Transactional
    public void deleteGoods(Long goodsCode, String userId) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));

        Goods goods = jpaGoodsRepository.findById(goodsCode)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_POST));

        // 장바구니에서 상품 존재하는지 확인 및 삭제할 엔티티 가져오기
        CartGoods cartGoods = cartGoodsRepository.findByUserAndGoods(user, goods)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_POST));

        // 장바구니에서 해당 상품 삭제
        cartGoodsRepository.delete(cartGoods);
    }

}
