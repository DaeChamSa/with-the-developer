package com.developer.cartservice.cartGoods.command.infrastructure.repository;

import com.developer.cartservice.cartGoods.command.domain.aggregate.CartGoods;
import com.developer.cartservice.cartGoods.command.domain.aggregate.CartGoodsCompositeKey;
import com.developer.cartservice.dto.GoodsResponseDTO;
import com.developer.cartservice.dto.ResponseUserDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartGoodsRepository extends JpaRepository<CartGoods, CartGoodsCompositeKey> {

    // 장바구니 굿즈 중복 체크
    boolean existsByUserCodeAndGoodsCode(Long userCode, Long goodsCode);

    // 장바구니 굿즈 삭제
    Optional<CartGoods> findByUserCodeAndGoodsCode(Long userCode, Long goodsCode);
}
