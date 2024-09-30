package com.developer.goods.query.service;

import com.developer.common.exception.CustomException;
import com.developer.common.exception.ErrorCode;
import com.developer.goods.query.dto.GoodsResponseDTO;
import com.developer.goods.query.mapper.GoodsMapper;
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

    // 굿즈 전체 상품 조회
    @Transactional
    public List<GoodsResponseDTO> selectAllGoods(Integer page) {
        int offset = (page - 1) * 10;

        List<GoodsResponseDTO> goodsList = sqlSession.getMapper(GoodsMapper.class).selectAllGoods(offset);

        return goodsList;
    }

    // 굿즈 코드로 조회
    @Transactional
    public GoodsResponseDTO selectGoodsByCode(Long goodsCode) {

        GoodsResponseDTO goodsResponseDTO = sqlSession.getMapper(GoodsMapper.class).selectGoodsByCode(goodsCode);

        if (goodsCode == null) {
            throw new CustomException(ErrorCode.NOT_FOUND_POST);
        }
        return goodsResponseDTO;
    }
}
