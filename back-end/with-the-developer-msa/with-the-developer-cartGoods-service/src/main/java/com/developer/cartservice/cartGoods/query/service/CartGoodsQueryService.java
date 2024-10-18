package com.developer.cartservice.cartGoods.query.service;

import com.developer.cartservice.cartGoods.query.dto.CartGoodsQueryDTO;
import com.developer.cartservice.cartGoods.query.mapper.CartGoodsMapper;
import com.developer.cartservice.client.ImageServiceClient;
import com.developer.cartservice.client.UserServiceClient;
import com.developer.cartservice.common.exception.CustomException;
import com.developer.cartservice.common.exception.ErrorCode;
import com.developer.cartservice.dto.ImageUploadDTO;
import com.developer.cartservice.dto.ResponseUserDTO;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CartGoodsQueryService {

    private final CartGoodsMapper cartGoodsMapper;
    private final UserServiceClient userServiceClient;
    private final ImageServiceClient imageServiceClient;

    // 장바구니 굿즈 조회
    @Transactional(readOnly = true)
    public List<CartGoodsQueryDTO> selectCartGoodsList(Integer page, String userId) {
        ResponseUserDTO user;
        try {
            user = userServiceClient.findByUserID(userId);
        } catch(FeignException e) {
            throw new CustomException(ErrorCode.NOT_FOUND_USER);
        }

        int offset = (page - 1) * 10;

        List<CartGoodsQueryDTO> goodsList = cartGoodsMapper.selectCartGoodsList(user.getUserCode());

        //장바구니 Null 확인
        if(goodsList == null || goodsList.size() == 0) {
            throw new CustomException(ErrorCode.NOT_FOUND_POST);
        }

        //각 굿즈의 이미지 조회 및 DTO에 설정
        for(CartGoodsQueryDTO dto : goodsList) {
            List<ImageUploadDTO> images = imageServiceClient.findByGoodsCode(dto.getGoodsGoodsCode());
            dto.setImages(images);
        }

        return goodsList;
    }
}
