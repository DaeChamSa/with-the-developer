package com.developer.project.comment.command.application.controller;

import com.developer.client.UserServiceClient;
import com.developer.common.success.SuccessCode;
import com.developer.config.TokenDecoder;
import com.developer.project.comment.command.application.dto.ProjCmtRequestDTO;
import com.developer.project.comment.command.application.service.ProjCmtCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RequiredArgsConstructor
@RequestMapping("/proj/post")
@RestController
public class ProjCmtCommandController {

    private final ProjCmtCommandService projCmtCommandService;
    private final TokenDecoder tokenDecoder;
    private final UserServiceClient userServiceClient;

    @PostMapping("/{projPostCode}/cmt")
    public ResponseEntity<Void> createCmt(
            @RequestHeader("Authorization") String token,
            @PathVariable("projPostCode") Long projPostCode,
            @RequestBody ProjCmtRequestDTO projCmtRequestDTO
    ) {

        Long currentUserCode = tokenDecoder.getUserCodeFromToken(token);

        userServiceClient.userCheck(currentUserCode);

        Long projCmtCode = projCmtCommandService.createCmt(projPostCode, currentUserCode, projCmtRequestDTO);

        return ResponseEntity.created(URI.create("/proj/post/" + projPostCode + "/cmt" + projCmtCode)).build();
    }

    @PutMapping("/{projPostCode}/cmt/{projCmtCode}")
    public ResponseEntity<SuccessCode> updateCmt(
            @RequestHeader("Authorization") String token,
            @PathVariable("projPostCode") Long projPostCode,
            @PathVariable("projCmtCode") Long projCmtCode,
            @RequestBody ProjCmtRequestDTO projCmtRequestDTO
    ) {

        Long currentUserCode = tokenDecoder.getUserCodeFromToken(token);

        userServiceClient.userCheck(currentUserCode);

        projCmtCommandService.updateCmt(projPostCode, currentUserCode, projCmtCode, projCmtRequestDTO);

        return ResponseEntity.ok(SuccessCode.PROJ_COMMENT_UPDATE_OK);
    }

    @DeleteMapping("/{projPostCode}/cmt/{projCmtCode}")
    public ResponseEntity<SuccessCode> deleteCmt(
            @RequestHeader("Authorization") String token,
            @PathVariable("projPostCode") Long projPostCode,
            @PathVariable("projCmtCode") Long projCmtCode) {
        Long currentUserCode = tokenDecoder.getUserCodeFromToken(token);
        projCmtCommandService.deleteCmt(projPostCode, currentUserCode, projCmtCode);

        return ResponseEntity.ok(SuccessCode.PROJ_COMMENT_DELETE_OK);
    }
}
