package com.developer.cartGoods.command.domain.aggregate;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

//CartGoods의 복합키 관련 클래스
@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class CartGoodsCompositeKey implements Serializable {

    private Long goodsCode;

    private Long userCode;
}