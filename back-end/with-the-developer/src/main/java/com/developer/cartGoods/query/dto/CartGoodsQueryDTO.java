package com.developer.cartGoods.query.dto;

import com.developer.image.command.entity.Image;
import lombok.Data;

import java.util.List;

@Data
public class CartGoodsQueryDTO {
    private int goods_code;
    private int goods_amount;
    private List<Image> images;
}
