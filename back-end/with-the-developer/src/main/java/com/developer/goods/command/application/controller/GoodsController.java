package com.developer.goods.command.application.controller;

import com.developer.common.module.PostAndImageService;
import com.developer.common.success.SuccessCode;
import com.developer.goods.command.application.dto.GoodsCreateDTO;
import com.developer.goods.command.application.dto.GoodsUpdateDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
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

    private final PostAndImageService postAndImageService;

    // 굿즈 등록
    @PostMapping(value = "/regist",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createGoods(
            @RequestPart(value = "goodsCreateDTO") GoodsCreateDTO goodsCreateDTO,
            @RequestPart(value = "images", required = false)
            MultipartFile[] images) throws IOException, ParseException {

        Long goodsCode = postAndImageService.goodsRegist(goodsCreateDTO, images);


        URI location = URI.create("/goods/" + goodsCode);

        return ResponseEntity.created(location).build();
    }

    // 굿즈 수정
    @PutMapping(value = "/update",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SuccessCode> updateGoods(
            @RequestPart(value = "goodsUpdateDTO") GoodsUpdateDTO goodsUpdateDTO,
            @RequestPart(value = "images", required = false) MultipartFile[] images
    ) throws IOException, ParseException {

        postAndImageService.goodsUpdate(goodsUpdateDTO, images);

        return ResponseEntity.ok().build();
    }

    // 굿즈 삭제
    @DeleteMapping("/delete/{goodsCode}")
    public ResponseEntity<Void> deleteGoods(@PathVariable(name = "goodsCode") Long goodsCode) throws Exception {

        postAndImageService.goodsDelete(goodsCode);

        return ResponseEntity.ok().build();
    }

}
