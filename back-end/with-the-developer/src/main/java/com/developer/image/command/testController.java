package com.developer.image.command;

import com.developer.image.command.service.ImageService;
import jakarta.persistence.Column;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
@RequestMapping("/image")
@Slf4j
public class testController {

    private final ImageService imageService;

    @PostMapping("/upload")
    public ResponseEntity<String> testImageUpload(
            @RequestPart MultipartFile[] files
    ) throws IOException {

        imageService.upload(files, "teamPost", 1L);

        return ResponseEntity.ok("image 업로드 완료");
    }

    @PostMapping("/update")
    public ResponseEntity<String> testImageUpdate(
            @RequestPart MultipartFile[] files
    ) throws IOException {

        imageService.updateImage(files, "teamPost", 1L);

        return ResponseEntity.ok("image 업로드 완료");
    }
}
