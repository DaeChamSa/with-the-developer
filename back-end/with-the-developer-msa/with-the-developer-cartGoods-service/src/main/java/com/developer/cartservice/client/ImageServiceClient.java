package com.developer.cartservice.client;

import com.developer.cartservice.config.FeignClientConfig;
import com.developer.cartservice.dto.ImageUploadDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name="with-the-developer-image-service", url="localhost:8000", configuration = FeignClientConfig.class)
public interface ImageServiceClient {

    @GetMapping("/post-service/image/{goodsCode}")
    List<ImageUploadDTO> findByGoodsCode(@PathVariable("goodsCode") Long GoodsCode);
}
