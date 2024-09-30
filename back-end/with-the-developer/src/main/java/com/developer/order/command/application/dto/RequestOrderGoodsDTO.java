package com.developer.order.command.application.dto;

import lombok.Data;

import java.util.List;

@Data
public class RequestOrderGoodsDTO {
    List<OrderGoodsDTO> orderGoods;
}
