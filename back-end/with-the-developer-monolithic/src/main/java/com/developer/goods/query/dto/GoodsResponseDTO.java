package com.developer.goods.query.dto;

import com.developer.image.command.entity.Image;
import lombok.Data;

import java.util.List;

@Data
public class GoodsResponseDTO {
    private long goodsCode;
    private String goodsName;
    private String goodsContent;
    private String goodsStatus;
    private int goodsPrice;
    private List<Image> images;
}
