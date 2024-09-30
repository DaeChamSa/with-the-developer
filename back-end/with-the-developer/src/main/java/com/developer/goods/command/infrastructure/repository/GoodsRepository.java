package com.developer.goods.command.infrastructure.repository;

import com.developer.goods.command.domain.Goods;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoodsRepository extends JpaRepository<Goods, Long> {
}
