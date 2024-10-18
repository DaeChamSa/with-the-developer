package com.developer.postservice.common.module;

import com.developer.postservice.goods.command.application.service.GoodsService;
import com.developer.postservice.goods.command.domain.Goods;
import com.developer.postservice.goods.command.infrastructure.repository.JpaGoodsRepository;
import com.developer.postservice.goods.command.domain.Goods;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class GoodsInitializer {

    private final JpaGoodsRepository goodsRepository;

    @PostConstruct
    public void init() {
        if (goodsRepository.findAll().isEmpty()) {
            Goods goods = new Goods(
                    "테스트 상품1",
                    "테스트 상품입니다.",
                    "판매중",
                    1000
            );
            goodsRepository.save(goods);

            Goods goods2 = new Goods(
                    "테스트 상품2",
                    "테스트 두번째 상품입니다.",
                    "판매중",
                    1000
            );
            goodsRepository.save(goods2);
        }

    }
}
