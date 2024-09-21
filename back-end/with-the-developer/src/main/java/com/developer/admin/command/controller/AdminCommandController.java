package com.developer.admin.command.controller;

import com.developer.admin.command.dto.AdminRecruitApplyUpdateDTO;
import com.developer.admin.command.service.AdminCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminCommandController {

    private final AdminCommandService adminCommandService;
    // 채용공고 등록 신청 승인
    @PutMapping("/recruit/approve/{recruitCode}")
    public ResponseEntity<String> apprRecruitApply(
            @PathVariable Long recruitCode,
            @RequestBody AdminRecruitApplyUpdateDTO adminrecruitApplyUpdateDTO
    ) {
        adminCommandService.updateRecruitApply(recruitCode, adminrecruitApplyUpdateDTO);
        return ResponseEntity.ok("채용공고 등록 신청 승인 완료");
    }

    // 채용공고 등록 신청 반려
    @PutMapping("/recruit/reject/{recruitCode}")
    public ResponseEntity<String> rejectRecruitApply(
            @PathVariable Long recruitCode,
            @RequestBody AdminRecruitApplyUpdateDTO adminrecruitApplyUpdateDTO)
    {
        adminCommandService.updateRecruitApply(recruitCode, adminrecruitApplyUpdateDTO);
        System.out.println(adminrecruitApplyUpdateDTO.getRecruitStatus());
        return ResponseEntity.ok("채용공고 등록 신청 반려 완료");
    }
}