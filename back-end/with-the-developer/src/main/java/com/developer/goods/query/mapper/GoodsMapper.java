package com.developer.goods.query.mapper;

import com.developer.goods.query.dto.GoodsResponseDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface GoodsMapper {
    List<GoodsResponseDTO> selectAllGoods(int offset);

    GoodsResponseDTO selectGoodsByCode(@Param("goodsCode") Long goodsCode);
}
