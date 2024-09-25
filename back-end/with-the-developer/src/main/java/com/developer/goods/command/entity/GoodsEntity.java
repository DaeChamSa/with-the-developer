package com.developer.goods.command.entity;

import com.developer.common.util.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name="goods")
@Getter
public class GoodsEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long goodsCode;
    private String goodsName;
    private String goodsContent;
    private int goodsStock;
    private String goodsStatus;
    private int goodsPrice;

    public GoodsEntity() {
    }

    // 굿즈 등록
    public GoodsEntity(String goodsName, String goodsContent, int goodsStock, String goodsStatus, int goodsPrice) {
        this.goodsName = goodsName;
        this.goodsContent = goodsContent;
        this.goodsStock = goodsStock;
        this.goodsStatus = goodsStatus;
        this.goodsPrice = goodsPrice;
    }

    // 굿즈 업데이트
    public void updateGoods(String goodsName, String goodsContent, int goodsStock, String goodsStatus, int goodsPrice) {
        this.goodsName = goodsName;
        this.goodsContent = goodsContent;
        this.goodsStock = goodsStock;
        this.goodsStatus = goodsStatus;
        this.goodsPrice = goodsPrice;
    }


}
