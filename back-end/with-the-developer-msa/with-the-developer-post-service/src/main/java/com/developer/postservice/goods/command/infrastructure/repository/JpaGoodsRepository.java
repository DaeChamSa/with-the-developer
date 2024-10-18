package com.developer.postservice.goods.command.infrastructure.repository;

import com.developer.postservice.goods.command.domain.Goods;
import com.developer.postservice.goods.command.domain.Goods;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaGoodsRepository extends JpaRepository<Goods, Long> {
    Goods findByGoodsName(String goodsName);
}
