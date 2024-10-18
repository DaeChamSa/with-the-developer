package com.developer.cartGoods.command.infrastructure.repository;

import com.developer.cartGoods.command.domain.aggregate.CartGoods;
import com.developer.cartGoods.command.domain.aggregate.CartGoodsCompositeKey;
import com.developer.goods.command.domain.Goods;
import com.developer.user.command.domain.aggregate.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartGoodsRepository extends JpaRepository<CartGoods, CartGoodsCompositeKey> {

    // 장바구니 굿즈 중복 체크
    boolean existsByUserAndGoods(User user, Goods cartGoods);

    // 장바구니 굿즈 삭제
    Optional<CartGoods> findByUserAndGoods(User user, Goods cartGoods);
}
