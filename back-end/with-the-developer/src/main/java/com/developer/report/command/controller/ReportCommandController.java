package com.developer.report.command.controller;

import com.developer.common.SuccessCode;
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





    // 채용공고 게시물 신고
    @PostMapping("/create")
    public ResponseEntity<String> createRecruit(
            @RequestBody ReportCreateDTO reportCreateDTO,
            @RequestParam Long postCode,
            @RequestParam ReportType reportType
            ) {
        Long loggedUserCode = SecurityUtil.getCurrentUserCode();

        Long reportCode = reportCommandService.createRecruitReport(reportCreateDTO, loggedUserCode, postCode, reportType);

        return ResponseEntity
                .created(URI.create("/report/create/" + reportCode))
                .build();
    }
}
