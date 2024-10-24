package com.developer.cartGoods.query.dto;

import com.developer.image.command.entity.Image;
import lombok.Data;

import java.util.List;

@Data
public class CartGoodsQueryDTO {
    private long goodsCode;
    private int goodsAmount;
    private List<Image> images;
}
