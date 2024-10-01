package com.developer.goods.command.domain;

import com.developer.common.util.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.SQLDelete;

@Entity
@Table(name = "goods")
@Getter
@SQLDelete(sql = "UPDATE goods SET del_status = true WHERE goods_code=?")
public class Goods extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long goodsCode;
    private String goodsName;
    private String goodsContent;
    private String goodsStatus;
    private int goodsPrice;

    public Goods() {
    }

    // 굿즈 등록
    public Goods(String goodsName, String goodsContent, String goodsStatus, int goodsPrice) {
        this.goodsName = goodsName;
        this.goodsContent = goodsContent;
        this.goodsStatus = goodsStatus;
        this.goodsPrice = goodsPrice;
    }

    // 굿즈 업데이트
    public void updateGoods(String goodsName, String goodsContent, String goodsStatus, int goodsPrice) {
        this.goodsName = goodsName;
        this.goodsContent = goodsContent;
        this.goodsStatus = goodsStatus;
        this.goodsPrice = goodsPrice;
    }
}
