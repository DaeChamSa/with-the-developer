package com.developer.recruit.command.controller;

import com.developer.common.SuccessCode;
import com.developer.recruit.command.service.RecruitCommandService;
import com.developer.recruit.command.dto.RecruitApplyDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/recruit")
@RequiredArgsConstructor
public class RecruitCommandController {

    private final RecruitCommandService recruitService;

    // 채용공고 등록 신청
    @PostMapping("/apply")
    public ResponseEntity<String> applyRecruit(
            @Valid @RequestBody RecruitApplyDTO newApplyRecruitDTO,
            HttpServletRequest request)
    {
        // 로그인 되어 있는지 체크. 로그인 되어 있지 않으면 에러, 되어 있다면 로그인 되어 있는 회원 userCode 반환
        Long loggedUserCode = recruitService.getLoggedUser(request).getUserCode();

        Long recruitCode = recruitService.applyRecruit(newApplyRecruitDTO, loggedUserCode);

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
        recruitService.deleteRecruit(recruitCode, loggedUserCode);

        return ResponseEntity.ok(SuccessCode.RECRUIT_DELETE_OK);
    }
}



