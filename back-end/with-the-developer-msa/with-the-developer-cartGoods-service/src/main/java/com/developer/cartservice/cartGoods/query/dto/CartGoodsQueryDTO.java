package com.developer.cartservice.cartGoods.query.dto;

import com.developer.cartservice.dto.ImageUploadDTO;
import lombok.Data;

import java.util.List;

@Data
public class CartGoodsQueryDTO {
    private long goodsGoodsCode;
    private int goodsAmount;
    private List<ImageUploadDTO> images;
}
