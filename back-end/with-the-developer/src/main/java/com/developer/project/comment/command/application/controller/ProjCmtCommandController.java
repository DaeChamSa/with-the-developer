package com.developer.project.comment.command.application.controller;

import com.developer.common.success.SuccessCode;
import com.developer.project.comment.command.application.dto.ProjCmtRequestDTO;
import com.developer.project.comment.command.application.service.ProjCmtCommandService;
import com.developer.user.security.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RequiredArgsConstructor
@RequestMapping("/proj/post")
@RestController
public class ProjCmtCommandController {

    private final ProjCmtCommandService projCmtCommandService;

    @PostMapping("/{projPostCode}/cmt")
    public ResponseEntity<Void> createCmt(@PathVariable("projPostCode") Long projPostCode, @RequestBody ProjCmtRequestDTO projCmtRequestDTO) {
        Long currentUserCode = getCurrentUserCode();
        Long projCmtCode = projCmtCommandService.createCmt(projPostCode, currentUserCode, projCmtRequestDTO);

        return ResponseEntity.created(URI.create("/proj/post/" + projPostCode + "/cmt" + projCmtCode)).build();
    }

    private Long getCurrentUserCode() {
        return SecurityUtil.getCurrentUserCode();
    }

    @PutMapping("/{projPostCode}/cmt/{projCmtCode}")
    public ResponseEntity<SuccessCode> updateCmt(
            @PathVariable("projPostCode") Long projPostCode,
            @PathVariable("projCmtCode") Long projCmtCode,
            @RequestBody ProjCmtRequestDTO projCmtRequestDTO) {
        Long currentUserCode = getCurrentUserCode();
        projCmtCommandService.updateCmt(projPostCode, currentUserCode, projCmtCode, projCmtRequestDTO);

        return ResponseEntity.ok(SuccessCode.PROJ_COMMENT_UPDATE_OK);
    }

    @DeleteMapping("/{projPostCode}/cmt/{projCmtCode}")
    public ResponseEntity<SuccessCode> deleteCmt(
            @PathVariable("projPostCode") Long projPostCode,
            @PathVariable("projCmtCode") Long projCmtCode) {
        Long currentUserCode = getCurrentUserCode();
        projCmtCommandService.deleteCmt(projPostCode, currentUserCode, projCmtCode);

        return ResponseEntity.ok(SuccessCode.PROJ_COMMENT_DELETE_OK);
    }
}
