package com.developer.cartservice.cartGoods.command.application.service;

import com.developer.cartservice.cartGoods.command.application.dto.CartGoodsAddDTO;
import com.developer.cartservice.cartGoods.command.domain.aggregate.CartGoods;
import com.developer.cartservice.cartGoods.command.domain.aggregate.CartGoodsCompositeKey;
import com.developer.cartservice.cartGoods.command.infrastructure.repository.CartGoodsRepository;
import com.developer.cartservice.client.GoodsServiceClient;
import com.developer.cartservice.client.UserServiceClient;
import com.developer.cartservice.common.exception.CustomException;
import com.developer.cartservice.common.exception.ErrorCode;
import com.developer.cartservice.dto.GoodsResponseDTO;
import com.developer.cartservice.dto.ResponseUserDTO;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class CartGoodsService {

    private final CartGoodsRepository cartGoodsRepository;
    private final UserServiceClient userServiceClient;
    private final GoodsServiceClient goodsServiceClient;

    // 장바구니 굿즈 추가
    @Transactional
    public void addCart(CartGoodsAddDTO cartGoodsAddDTO, String userId) {
        ResponseUserDTO user;
        try {
            user = userServiceClient.findByUserID(userId);
        } catch(FeignException e) {
            throw new CustomException(ErrorCode.NOT_FOUND_USER);
        }
        GoodsResponseDTO goods;
        try {
            goods = goodsServiceClient.selectGoodsByCode(cartGoodsAddDTO.getGoodsCode());
        } catch(FeignException e) {
            throw new CustomException(ErrorCode.NOT_FOUND_POST);
        }

        // 중복 체크: 이미 해당 유저가 해당 상품을 장바구니에 추가했는지 확인
        boolean exists = cartGoodsRepository.existsByUserCodeAndGoodsCode(user.getUserCode(), goods.getGoodsCode());
        if (exists) {
            throw new CustomException(ErrorCode.DUPLICATE_VALUE);  // 중복된 상품이 있으면 에러 처리
        }

        CartGoods cartGoods = new CartGoods(user.getUserCode(), goods.getGoodsCode(), cartGoodsAddDTO.getGoodsAmount());

        cartGoodsRepository.save(cartGoods);
    }

    // 장바구니 굿즈 삭제
    @Transactional
    public void deleteGoods(Long goodsCode, String userId) {
        ResponseUserDTO user;
        try {
            user = userServiceClient.findByUserID(userId);
        } catch(FeignException e) {
            throw new CustomException(ErrorCode.NOT_FOUND_USER);
        }
        GoodsResponseDTO goods;
        try {
            goods = goodsServiceClient.selectGoodsByCode(goodsCode);
        } catch(FeignException e) {
            throw new CustomException(ErrorCode.NOT_FOUND_POST);
        }

        // 장바구니에서 상품 존재하는지 확인 및 삭제할 엔티티 가져오기
        CartGoods cartGoods = cartGoodsRepository.findByUserCodeAndGoodsCode(user.getUserCode(), goods.getGoodsCode())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_POST));

        // 장바구니에서 해당 상품 삭제
        cartGoodsRepository.delete(cartGoods);
    }

}
