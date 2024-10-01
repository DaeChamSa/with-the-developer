package com.developer.cartGoods.query.service;

import com.developer.cartGoods.query.dto.CartGoodsQueryDTO;
import com.developer.cartGoods.query.mapper.CartGoodsMapper;
import com.developer.common.exception.CustomException;
import com.developer.common.exception.ErrorCode;
import com.developer.image.command.entity.Image;
import com.developer.image.command.repository.ImageRepository;
import com.developer.user.command.entity.User;
import com.developer.user.command.repository.UserRepository;
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
    private final ImageRepository imageRepository;
    private final UserRepository userRepository;

    // 장바구니 굿즈 조회
    @Transactional(readOnly = true)
    public List<CartGoodsQueryDTO> selectCartGoodsList(Integer page, String userId) {
        User user = userRepository.findByUserId(userId).orElseThrow(()->new CustomException(ErrorCode.NOT_FOUND_USER));

        int offset = (page - 1) * 10;

        List<CartGoodsQueryDTO> goodsList = cartGoodsMapper.selectCartGoodsList(offset, user.getUserCode());

        //장바구니 Null 확인
        if(goodsList == null || goodsList.size() == 0) {
            throw new CustomException(ErrorCode.NOT_FOUND_POST);
        }

        log.info("Null 확인 Retrieved goods list: {}", goodsList);

        //각 굿즈의 이미지 조회 및 DTO에 설정
//        for(CartGoodsQueryDTO dto : goodsList) {
//            List<Image> images = imageRepository.findByGoodsCode((long)dto.getGoods_code());
//            dto.setImages(images);
//        }

        return goodsList;
    }
}
