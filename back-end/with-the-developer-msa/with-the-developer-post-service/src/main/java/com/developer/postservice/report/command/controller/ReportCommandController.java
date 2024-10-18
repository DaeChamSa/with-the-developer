package com.developer.postservice.report.command.controller;

import com.developer.postservice.client.UserServiceClient;
import com.developer.postservice.common.exception.CustomException;
import com.developer.postservice.common.exception.ErrorCode;
import com.developer.postservice.report.command.dto.ReportCreateDTO;
import com.developer.postservice.report.command.dto.ReportCreateDTO;
import com.developer.postservice.report.command.entity.ReportType;
import com.developer.postservice.report.command.service.ReportCommandService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@Tag(name = "report", description = "신고 API")
@RestController
@RequestMapping("/report")
@RequiredArgsConstructor
public class ReportCommandController {

    private final ReportCommandService reportCommandService;
    private final UserServiceClient userServiceClient;

    // 게시물 신고하기
    @PostMapping("/create")
    @Operation(summary = "게시물 신고", description = "게시판의 게시물을 신고합니다.")
    public ResponseEntity<String> createRecruit(
            @RequestBody ReportCreateDTO reportCreateDTO,
            @RequestParam(name = "postCode") Long postCode,
            @RequestParam(name = "reportTypePara") String reportTypePara
            )
    {
        ReportType reportType;

        // Enum으로 변환
        try {
            reportType = ReportType.valueOf(reportTypePara.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new CustomException(ErrorCode.INVALID_VALUE);
        }

        Long loggedUserCode = userServiceClient.getCurrentUserCode();
        Long repoCode = reportCommandService.createAndHandleReport(reportCreateDTO, loggedUserCode, postCode, reportType);

        return ResponseEntity
                .created(URI.create("/report/create/" + repoCode))
                .build();
    }
}
