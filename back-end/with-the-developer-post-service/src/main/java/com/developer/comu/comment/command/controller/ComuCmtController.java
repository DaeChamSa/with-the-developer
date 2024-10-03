package com.developer.comu.comment.command.controller;

import com.developer.comu.comment.command.dto.ComuCmtCreateDTO;
import com.developer.comu.comment.command.dto.ComuCmtUpdateDTO;
import com.developer.comu.comment.command.service.ComuCmtService;
import com.developer.config.TokenDecoder;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/comu-post/postlist")
public class ComuCmtController {

    private final ComuCmtService comuCmtService;
    private final TokenDecoder tokenDecoder;

    //커뮤니티 게시글 댓글 등록
    @PostMapping("/{comuPostCode}/cmt")
    public ResponseEntity<Void> createComuCmt(
            @RequestHeader("Authorization") String token,
            @PathVariable("comuPostCode") Long comuPostCode, @RequestBody ComuCmtCreateDTO comuCmtCreateDTO
    ) {
        Long currentUserCode = tokenDecoder.getUserCodeFromToken(token);
        Long comuCmtCode = comuCmtService.createComuCmt(comuPostCode, currentUserCode, comuCmtCreateDTO);
        return ResponseEntity.created(URI.create("/comu-post/postlist/" + comuPostCode + "/cmt/" + comuCmtCode)).build();
    }

    // 커뮤니티 게시글 댓글 수정
    @PutMapping("/{comuPostCode}/cmt-update")
    public ResponseEntity<Void> updateComuCmt(
            @RequestHeader("Authorization") String token,
            @PathVariable("comuPostCode") Long comuPostCode, @RequestBody ComuCmtUpdateDTO comuCmtUpdateDTO) {
        Long currentUserCode = tokenDecoder.getUserCodeFromToken(token);
        comuCmtService.updateComuCnt(comuPostCode, currentUserCode, comuCmtUpdateDTO);
        return ResponseEntity.noContent().build();
    }

    // 커뮤니티 게시글 댓글 삭제
    @DeleteMapping("/{comuPostCode}/cmt-delete/{comuCmt}")
    public ResponseEntity<Void> deleteComuCmt(
            @RequestHeader("Authorization") String token,
            @PathVariable Long comuPostCode, @PathVariable Long comuCmt, HttpServletRequest request) {
        Long currentUserCode = tokenDecoder.getUserCodeFromToken(token);
        comuCmtService.deleteComuCmt(comuPostCode, currentUserCode);

        return ResponseEntity.noContent().build();
    }

}