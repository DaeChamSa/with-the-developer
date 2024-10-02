package com.developer.jobTag.query.Controller;

import com.developer.jobTag.query.dto.JobTagReadDTO;
import com.developer.jobTag.query.service.JobTagQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/job-tag")
@RequiredArgsConstructor
public class JobTagQueryController {

    private final JobTagQueryService jobTagQueryService;

    // 직무태그 항목 조회(희망직무 선택시 조회하는데 사용)
    @GetMapping("/read")
    public ResponseEntity <List<JobTagReadDTO>> readJobTagList() {
        List<JobTagReadDTO> jobTagList = jobTagQueryService.readJobTagList();
        return ResponseEntity.ok(jobTagList);
    }
}