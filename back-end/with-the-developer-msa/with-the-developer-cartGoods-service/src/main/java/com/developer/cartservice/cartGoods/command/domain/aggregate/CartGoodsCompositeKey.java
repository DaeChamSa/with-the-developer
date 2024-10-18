package com.developer.cartservice.cartGoods.command.domain.aggregate;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

//CartGoods의 복합키 관련 클래스
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class CartGoodsCompositeKey implements Serializable {

    private Long userCode;

    private Long goodsCode;
}