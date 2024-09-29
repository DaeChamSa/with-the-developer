package com.developer.goods.command.application.controller;

import com.developer.goods.command.application.dto.GoodsUpdateDTO;
import com.developer.goods.command.application.service.GoodsService;
import com.developer.goods.command.application.dto.GoodsCreateDTO;
import com.developer.image.command.service.ImageService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.text.ParseException;

@Slf4j
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
            @RequestPart(value="images", required = false)
            MultipartFile[] images,
            HttpServletRequest httpServletRequest) throws IOException {

        Long goodsCode = goodsService.createGoods(goodsCreateDTO);

        // 이미지가 있다면 이미지 서비스 호출
        if (images != null && images.length > 0) {
            imageService.upload(images, "goods", goodsCode);
        }

        URI location = URI.create("/goods/" + goodsCode);

        return ResponseEntity.created(location).build();
    }

    // 굿즈 수정
    @PutMapping("/update")
    public ResponseEntity<Void> updateGoods(
            @RequestPart GoodsUpdateDTO goodsUpdateDTO,
            @RequestPart MultipartFile[] images,
            HttpServletRequest httpServletRequest
    ) throws IOException {

        imageService.updateImage(images, "goods", goodsUpdateDTO.getGoodsCode());

        goodsService.updateGoods(goodsUpdateDTO);

        return ResponseEntity.ok().build();
    }

    // 굿즈 삭제
    @DeleteMapping("/delete/{goodsCode}")
    public ResponseEntity<Void> deleteGoods(@PathVariable Long goodsCode, HttpServletRequest httpServletRequest) {
        imageService.deleteImage("goods", goodsCode);
        goodsService.deleteGoods(goodsCode);

        return ResponseEntity.ok().build();
    }

}
