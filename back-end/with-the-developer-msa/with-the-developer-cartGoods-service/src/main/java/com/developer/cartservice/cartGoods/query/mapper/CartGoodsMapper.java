package com.developer.cartservice.cartGoods.query.mapper;

import com.developer.cartservice.cartGoods.query.dto.CartGoodsQueryDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CartGoodsMapper {

    List<CartGoodsQueryDTO> selectCartGoodsList(@Param("userCode")Long userCode);
}
