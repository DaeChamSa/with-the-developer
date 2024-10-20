package com.developer.goods.command.infrastructure.repository;

import com.developer.goods.command.domain.Goods;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaGoodsRepository extends JpaRepository<Goods, Long> {
    Goods findByGoodsName(String goodsName);
}
