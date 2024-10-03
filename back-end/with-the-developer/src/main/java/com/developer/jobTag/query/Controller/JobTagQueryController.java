package com.developer.jobTag.query.Controller;

import com.developer.jobTag.query.dto.JobTagReadDTO;
import com.developer.jobTag.query.service.JobTagQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "job-tag", description = "직무태그 API")
@RestController
@RequestMapping("/job-tag")
@RequiredArgsConstructor
public class JobTagQueryController {

    private final JobTagQueryService jobTagQueryService;

    // 직무태그 항목 조회(희망직무 선택시 조회하는데 사용)
    @GetMapping("/read")
    @Operation(summary = "직무태그 조회", description = "등록되어 있는 직무태그를 조회합니다.")
    public ResponseEntity <List<JobTagReadDTO>> readJobTagList() {
        List<JobTagReadDTO> jobTagList = jobTagQueryService.readJobTagList();
        return ResponseEntity.ok(jobTagList);
    }
}