package com.developer.goods.query.service;

import com.developer.common.exception.CustomException;
import com.developer.common.exception.ErrorCode;
import com.developer.goods.query.dto.GoodsResponseDTO;
import com.developer.goods.query.mapper.GoodsMapper;
import com.developer.image.command.entity.Image;
import com.developer.image.command.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class GoodsQueryService {

    private final SqlSession sqlSession;
    private final ImageRepository imageRepository;

    // 굿즈 전체 상품 조회
    @Transactional
    public List<GoodsResponseDTO> selectAllGoods(Integer page) {
        int offset = (page - 1) * 10;

        List<GoodsResponseDTO> goodsList = sqlSession.getMapper(GoodsMapper.class).selectAllGoods(offset);

        // 굿즈 리스트 이미지 조회
        for(GoodsResponseDTO goods : goodsList) {
            List<Image> images = imageRepository.findByGoodsCode(goods.getGoodsCode());
            goods.setImages(images);
        }

        return goodsList;
    }

    // 굿즈 코드로 조회
    @Transactional
    public GoodsResponseDTO selectGoodsByCode(Long goodsCode) {

        GoodsResponseDTO goodsResponseDTO = sqlSession.getMapper(GoodsMapper.class).selectGoodsByCode(goodsCode);

        if (goodsCode == null || goodsCode < 0) {
            throw new CustomException(ErrorCode.NOT_FOUND_POST);
        }
        goodsResponseDTO.setImages(imageRepository.findByGoodsCode(goodsCode));


        return goodsResponseDTO;
    }
}
