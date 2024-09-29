package com.developer.goods.command.application.service;

import com.developer.common.exception.CustomException;
import com.developer.common.exception.ErrorCode;
import com.developer.goods.command.application.dto.GoodsUpdateDTO;
import com.developer.goods.command.application.dto.GoodsCreateDTO;
import com.developer.goods.command.domain.GoodsEntity;
import com.developer.goods.command.infrastructure.repository.JpaGoodsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class GoodsService {

    private final JpaGoodsRepository goodsRepository;

    // 굿즈 등록
    @Transactional
    public Long createGoods(GoodsCreateDTO goodsCreateDTO) {

        GoodsEntity goodsEntity = new GoodsEntity(
                goodsCreateDTO.getGoodsName(),
                goodsCreateDTO.getGoodsContent(),
                goodsCreateDTO.getGoodsStock(),
                goodsCreateDTO.getGoodsStatus(),
                goodsCreateDTO.getGoodsPrice());

        GoodsEntity savedGoods = goodsRepository.save(goodsEntity);
        return savedGoods.getGoodsCode();
    }


    // 굿즈 수정
    @Transactional
    public void updateGoods(GoodsUpdateDTO goodsUpdateDTO) {
        GoodsEntity goodsEntity = goodsRepository.findById(goodsUpdateDTO.getGoodsCode())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_POST));

        goodsEntity.updateGoods(
                goodsUpdateDTO.getGoodsName(),
                goodsUpdateDTO.getGoodsContent(),
                goodsUpdateDTO.getGoodsStock(),
                goodsUpdateDTO.getGoodsStatus(),
                goodsUpdateDTO.getGoodsPrice());


    }

    // 굿즈 삭제
    @Transactional
    public void deleteGoods(Long goodsCode) {
        GoodsEntity goodsEntity = goodsRepository.findById(goodsCode)
                .orElseThrow(()-> new CustomException(ErrorCode.NOT_FOUND_POST));
    }
}
