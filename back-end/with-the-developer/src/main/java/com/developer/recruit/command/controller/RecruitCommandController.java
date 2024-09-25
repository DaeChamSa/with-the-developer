package com.developer.recruit.command.controller;

import com.developer.common.SuccessCode;
import com.developer.image.command.service.ImageService;
import com.developer.recruit.command.service.RecruitCommandService;
import com.developer.recruit.command.dto.RecruitApplyDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;

@RestController
@RequestMapping("/recruit")
@RequiredArgsConstructor
public class RecruitCommandController {

    private final RecruitCommandService recruitService;
    private final ImageService imageService;

    // 채용공고 등록 신청
    @PostMapping("/apply")
    public ResponseEntity<String> applyRecruit(
            @Valid @RequestPart RecruitApplyDTO newApplyRecruitDTO,
            @RequestPart MultipartFile[] images,
            HttpServletRequest request) throws IOException {
        // 로그인 되어 있는지 체크. 로그인 되어 있지 않으면 에러, 되어 있다면 로그인 되어 있는 회원 userCode 반환
        Long loggedUserCode = recruitService.getLoggedUser(request).getUserCode();

        Long recruitCode = recruitService.applyRecruit(newApplyRecruitDTO, loggedUserCode);

        // 이미지가 있다면 이미지 서비스 호출
        if(images != null && images.length > 0) {
            imageService.upload(images, "recruit", recruitCode);
        }

        return ResponseEntity
                .created(URI.create("/recruit/apply/" + recruitCode))
                .build();
    }

    // 채용공고 수동 마감
    @PutMapping("/complete/{recruitCode}")
    public ResponseEntity<SuccessCode> completeRecruitManual(@PathVariable Long recruitCode, HttpServletRequest request) {
        Long loggedUserCode = recruitService.getLoggedUser(request).getUserCode();

        recruitService.completeRecruitManual(recruitCode, loggedUserCode);

        return ResponseEntity.ok(SuccessCode.RECRUIT_COMPLETE_OK);
    }

    // 채용공고 삭제
    @DeleteMapping("/delete/{recruitCode}")
    public ResponseEntity<SuccessCode> deleteRecruit(@PathVariable Long recruitCode, HttpServletRequest request) throws Exception {
        Long loggedUserCode = recruitService.getLoggedUser(request).getUserCode();

        // 게시글 삭제 시 이미지도 같이 삭제
        imageService.deleteImage("recruit", recruitCode);

        recruitService.deleteRecruit(recruitCode, recruitCode);

        return ResponseEntity.ok(SuccessCode.RECRUIT_DELETE_OK);
    }
}



