package com.developer.orderservice.order.query.mapper;

import com.developer.orderservice.order.query.dto.OrderGoodsListDTO;
import com.developer.orderservice.order.query.dto.OrderListDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderMapper {

    List<OrderGoodsListDTO> findOrderGoodsByOrderCode(Long orderCode);

    List<OrderListDTO> findOrderByUserCode(Long userCode);
}
