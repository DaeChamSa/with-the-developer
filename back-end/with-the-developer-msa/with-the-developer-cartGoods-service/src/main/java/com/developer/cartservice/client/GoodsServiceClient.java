package com.developer.cartservice.client;

import com.developer.cartservice.config.FeignClientConfig;
import com.developer.cartservice.dto.GoodsResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="with-the-developer-goods-service", url="localhost:8000", configuration = FeignClientConfig.class)
public interface GoodsServiceClient {

    @GetMapping("/post-service/goods/goodslist/{goodsCode}")
    GoodsResponseDTO selectGoodsByCode(@PathVariable Long goodsCode);
}
