package com.developer.goods.command.controller;

import com.developer.goods.command.dto.GoodsCreateDTO;
import com.developer.goods.command.dto.GoodsUpdateDTO;
import com.developer.goods.command.service.GoodsService;
import com.developer.image.command.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/goods")
public class GoodsController {

    private final GoodsService goodsService;
    private final ImageService imageService;

    // 굿즈 등록
    @PostMapping("/regist")
    public ResponseEntity<Void> createGoods(
            @RequestPart GoodsCreateDTO goodsCreateDTO,
            @RequestPart MultipartFile[] images) throws IOException {

        Long goodsCode = goodsService.createGoods(goodsCreateDTO);

        // 이미지가 있다면 이미지 서비스 호출
        if (images != null && images.length > 0) {
            imageService.upload(images, "comuPost", goodsCode);
        }

        return ResponseEntity.ok().build();
    }

    // 굿즈 수정
    @PutMapping("/update")
    public ResponseEntity<Void> updateGoods(
            @RequestPart GoodsUpdateDTO goodsUpdateDTO,
            @RequestPart MultipartFile[] images
    ) throws IOException {

        imageService.updateImage(images, "goodsEntity", goodsUpdateDTO.getGoodsCode());

        goodsService.updateGoods(goodsUpdateDTO);

        return ResponseEntity.ok().build();
    }

}
