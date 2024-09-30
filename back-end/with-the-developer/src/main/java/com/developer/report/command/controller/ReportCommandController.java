package com.developer.report.command.controller;

import com.developer.common.exception.CustomException;
import com.developer.common.exception.ErrorCode;
import com.developer.report.command.dto.ReportCreateDTO;
import com.developer.report.command.entity.ReportType;
import com.developer.report.command.service.ReportCommandService;
import com.developer.user.security.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/report")
@RequiredArgsConstructor
public class ReportCommandController {

    private final ReportCommandService reportCommandService;

    // 게시물 신고하기
    @PostMapping("/create")
    public ResponseEntity<String> createRecruit(
            @RequestBody ReportCreateDTO reportCreateDTO,
            @RequestParam Long postCode,
            @RequestParam String reportTypePara
            )
    {
        ReportType reportType;

        // Enum으로 변환
        try {
            reportType = ReportType.valueOf(reportTypePara.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new CustomException(ErrorCode.INVALID_VALUE);
        }

        Long loggedUserCode = SecurityUtil.getCurrentUserCode();
        Long repoCode = reportCommandService.createAndHandleReport(reportCreateDTO, loggedUserCode, postCode, reportType);

        return ResponseEntity
                .created(URI.create("/report/create/" + repoCode))
                .build();
    }
}
