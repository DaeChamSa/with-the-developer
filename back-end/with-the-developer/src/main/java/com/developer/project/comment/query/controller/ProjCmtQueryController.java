package com.developer.project.comment.query.controller;

import com.developer.project.comment.query.dto.ProjCmtResponseDTO;
import com.developer.project.comment.query.service.ProjCmtQueryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "project-comment", description = "프로젝트 댓글 API")
@RestController
@RequiredArgsConstructor
public class ProjCmtQueryController {

    private final ProjCmtQueryService projCmtQueryService;

    @GetMapping("/proj/post/{projPostCode}/cmt")
    public ResponseEntity<List<ProjCmtResponseDTO>> readCmt(
            @PathVariable("projPostCode") Long projPostCode,
            @RequestParam(defaultValue = "1") Integer page) {
        List<ProjCmtResponseDTO> projCmtResponseDTO = projCmtQueryService.readAll(page, projPostCode);

        return ResponseEntity.ok(projCmtResponseDTO);
    }
}
