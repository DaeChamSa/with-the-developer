package com.developer.project.post.command.application.controller;

import com.developer.common.exception.CustomException;
import com.developer.common.exception.ErrorCode;
import com.developer.common.SuccessCode;
import com.developer.user.command.dto.TokenSaveDTO;
import com.developer.project.post.command.application.dto.ProjPostRequestDTO;
import com.developer.project.post.command.application.service.ProjPostCommandService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RequiredArgsConstructor
@RequestMapping("/proj")
@RestController
public class ProjPostCommandController {

    private final ProjPostCommandService projPostCommandService;

    @PostMapping("/post")
    public ResponseEntity<Void> createProjPost(
            @RequestBody ProjPostRequestDTO projPostRequestDTO, HttpServletRequest httpServletRequest) {
        Long loginUserCode = getUserCodeBySession(httpServletRequest);

        Long projPostCode = projPostCommandService.createProjPost(loginUserCode, projPostRequestDTO);

        return ResponseEntity.created(URI.create("/proj/post/" + projPostCode)).build();
    }

    @PutMapping("/post/{projPostCode}")
    public ResponseEntity<SuccessCode> updateProjPost(
            @PathVariable Long projPostCode,
            @RequestBody ProjPostRequestDTO projPostRequestDTO,
            HttpServletRequest httpServletRequest) {
        Long loginUserCode = getUserCodeBySession(httpServletRequest);

        projPostCommandService.updateProjPost(projPostCode, loginUserCode, projPostRequestDTO);

        return ResponseEntity.ok(SuccessCode.PROJ_POST_UPDATE_OK);
    }

    @DeleteMapping("/post/{projPostCode}")
    public ResponseEntity<SuccessCode> deleteProjPost(@PathVariable Long projPostCode, HttpServletRequest httpServletRequest) {
        Long loginUserCode = getUserCodeBySession(httpServletRequest);

        projPostCommandService.deleteProjPost(loginUserCode, projPostCode);

        return ResponseEntity.ok(SuccessCode.PROJ_POST_DELETE_OK);
    }

    private Long getUserCodeBySession(HttpServletRequest httpServletRequest) {
        TokenSaveDTO loginUser = (TokenSaveDTO) httpServletRequest.getSession().getAttribute("user");

        if (loginUser == null) {
            throw new CustomException(ErrorCode.NEED_LOGIN);
        }

        return loginUser.getUserCode();
    }
}
