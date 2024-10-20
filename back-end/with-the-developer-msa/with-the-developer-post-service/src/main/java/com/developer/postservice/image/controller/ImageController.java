package com.developer.postservice.image.controller;

import com.developer.postservice.client.UserServiceClient;
import com.developer.postservice.common.module.PostAndImageService;
import com.developer.postservice.comu.post.command.dto.ComuPostCreateDTO;
import com.developer.postservice.image.command.dto.ImageUploadDTO;
import com.developer.postservice.image.command.entity.Image;
import com.developer.postservice.image.command.repository.ImageRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/image")
public class ImageController {

    private final PostAndImageService postAndImageService;
    private final ImageRepository imageRepository;

    @GetMapping("/{goodsCode}")
    public ResponseEntity<List<ImageUploadDTO>> findByGoodsCode(@PathVariable("goodsCode") Long goodsCode) {
        List<Image> images = imageRepository.findByGoodsCode(goodsCode);

        List<ImageUploadDTO> imageUploadDTOs = images.stream()
                .map(image -> ImageUploadDTO.builder()
                        .originFileName(image.getOriginFileName())
                        .fileName(image.getFileName())
                        .fileType(image.getFileType())
                        .fileSize(image.getFileSize())
                        .code(image.getGoodsCode())
                        .build())
                .collect(Collectors.toList());
        return ResponseEntity.ok(imageUploadDTOs);
    }
}
