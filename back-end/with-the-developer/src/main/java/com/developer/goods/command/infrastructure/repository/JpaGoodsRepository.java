package com.developer.goods.command.infrastructure.repository;

import com.developer.goods.command.domain.GoodsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaGoodsRepository extends JpaRepository<GoodsEntity, Long> {
}
