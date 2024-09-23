package com.developer.image.command;

import com.developer.image.command.service.ImageService;
import jakarta.persistence.Column;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
@RequestMapping("/test")
public class testController {

    private final ImageService imageService;

    @PostMapping("/image")
    public ResponseEntity<String> testImage(@RequestPart MultipartFile[] file) throws IOException {

        if(file == null || file.length == 0) {
            return ResponseEntity.noContent().build();
        }

        imageService.upload(file,"images/teamPost");

        return ResponseEntity.ok("image 업로드 완료");
    }
}
