package com.developer.orderservice.client;

import com.developer.orderservice.config.FeignClientConfig;
import com.developer.orderservice.dto.GoodsResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="with-the-developer-goods-service", url="localhost:8000", configuration = FeignClientConfig.class)
public interface GoodsServiceClient {

    @GetMapping("/goods-service/goods/goodslist/{goodsCode}")
    GoodsResponseDTO selectGoodsByCode(@PathVariable Long goodsCode);
}
