package com.developer.orderservice.order.command.application.dto;

import lombok.Data;

import java.util.List;

@Data
public class RequestOrderGoodsDTO {
    List<OrderGoodsDTO> orderGoods;
}
