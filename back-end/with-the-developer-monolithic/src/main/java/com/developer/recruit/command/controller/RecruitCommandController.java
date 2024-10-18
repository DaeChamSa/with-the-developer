package com.developer.recruit.command.controller;

import com.developer.common.success.SuccessCode;
import com.developer.common.module.PostAndImageService;
import com.developer.recruit.command.service.RecruitCommandService;
import com.developer.recruit.command.dto.RecruitApplyDTO;
import com.developer.user.security.SecurityUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;

@Tag(name = "recruit", description = "채용공고 API")
@RestController
@RequestMapping("/recruit")
@RequiredArgsConstructor
public class RecruitCommandController {

    private final RecruitCommandService recruitService;
    private final PostAndImageService postAndImageService;

    // 채용공고 등록 신청
    @PostMapping(value = "/apply",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "채용공고 등록 신청", description = "새로운 채용공고를 등록 신청할 수 있다.")
    public ResponseEntity<String> applyRecruit(
            @Valid @RequestPart(name = "newApplyRecruitDTO") RecruitApplyDTO newApplyRecruitDTO,
            @RequestPart(value = "images", required = false) MultipartFile[] images
    ) throws IOException
    {
        // 로그인 되어 있는지 체크. 로그인 되어 있지 않으면 에러, 되어 있다면 로그인 되어 있는 회원 userCode 반환
        Long loggedUserCode = SecurityUtil.getCurrentUserCode();

        // 게시글 등록
        Long recruitCode = postAndImageService.recruitPostRegist(newApplyRecruitDTO, loggedUserCode, images);

        return ResponseEntity
                .created(URI.create("/recruit/apply/" + recruitCode))
                .build();
    }

    // 채용공고 수동 마감
    @PutMapping("/complete/{recruitCode}")
    @Operation(summary = "채용공고 수동 마감", description = "본인이 등록한 채용공고를 모집 마감일 이전에 수동으로 모집 마감합니다.")
    public ResponseEntity<SuccessCode> completeRecruitManual(@PathVariable Long recruitCode) {

        Long loggedUserCode = SecurityUtil.getCurrentUserCode();

        recruitService.completeRecruitManual(recruitCode, loggedUserCode);

        return ResponseEntity.ok(SuccessCode.RECRUIT_COMPLETE_OK);
    }

    // 채용공고 삭제
    @DeleteMapping("/delete/{recruitCode}")
    @Operation(summary = "채용공고 삭제", description = "본인이 등록한 채용공고를 삭제합니다.")
    public ResponseEntity<SuccessCode> deleteRecruit(@PathVariable Long recruitCode) throws Exception {

        Long loggedUserCode = SecurityUtil.getCurrentUserCode();

        // 게시글 삭제
        postAndImageService.recruitPostDelete(recruitCode, loggedUserCode);

        return ResponseEntity.ok(SuccessCode.RECRUIT_DELETE_OK);
    }
}



