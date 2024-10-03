package com.developer.project.comment.command.application.controller;

import com.developer.common.success.SuccessCode;
import com.developer.project.comment.command.application.dto.ProjCmtRequestDTO;
import com.developer.project.comment.command.application.service.ProjCmtCommandService;
import com.developer.user.security.SecurityUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@Tag(name = "project-comment", description = "프로젝트 댓글 API")
@RequiredArgsConstructor
@RequestMapping("/proj/post")
@RestController
public class ProjCmtCommandController {

    private final ProjCmtCommandService projCmtCommandService;

    @PostMapping("/{projPostCode}/cmt")
    @Operation(summary = "프로젝트 게시글 댓글 생성", description = "프로젝트 게시글에 새로운 댓글을 생성합니다.")
    public ResponseEntity<Void> createCmt(@PathVariable("projPostCode") Long projPostCode, @RequestBody ProjCmtRequestDTO projCmtRequestDTO) {
        Long currentUserCode = getCurrentUserCode();
        Long projCmtCode = projCmtCommandService.createCmt(projPostCode, currentUserCode, projCmtRequestDTO);

        return ResponseEntity.created(URI.create("/proj/post/" + projPostCode + "/cmt" + projCmtCode)).build();
    }

    private Long getCurrentUserCode() {
        return SecurityUtil.getCurrentUserCode();
    }

    @PutMapping("/{projPostCode}/cmt/{projCmtCode}")
    @Operation(summary = "프로젝트 게시글 댓글 수정", description = "프로젝트 게시글에 등록되어 있는 댓글을 수정합니다.")
    public ResponseEntity<SuccessCode> updateCmt(
            @PathVariable("projPostCode") Long projPostCode,
            @PathVariable("projCmtCode") Long projCmtCode,
            @RequestBody ProjCmtRequestDTO projCmtRequestDTO) {
        Long currentUserCode = getCurrentUserCode();
        projCmtCommandService.updateCmt(projPostCode, currentUserCode, projCmtCode, projCmtRequestDTO);

        return ResponseEntity.ok(SuccessCode.PROJ_COMMENT_UPDATE_OK);
    }

    @DeleteMapping("/{projPostCode}/cmt/{projCmtCode}")
    @Operation(summary = "프로젝트 게시글 댓글 삭제", description = "프로젝트 게시글에 등록되어 있는 댓글을 삭제합니다.")
    public ResponseEntity<SuccessCode> deleteCmt(
            @PathVariable("projPostCode") Long projPostCode,
            @PathVariable("projCmtCode") Long projCmtCode) {
        Long currentUserCode = getCurrentUserCode();
        projCmtCommandService.deleteCmt(projPostCode, currentUserCode, projCmtCode);

        return ResponseEntity.ok(SuccessCode.PROJ_COMMENT_DELETE_OK);
    }
}
