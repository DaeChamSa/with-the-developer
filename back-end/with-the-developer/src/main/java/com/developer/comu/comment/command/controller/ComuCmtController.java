package com.developer.comu.comment.command.controller;

import com.developer.comu.comment.command.dto.ComuCmtCreateDTO;
import com.developer.comu.comment.command.service.ComuCmtService;
import com.developer.user.security.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comu-post/postlist")
public class ComuCmtController {

    private final ComuCmtService comuCmtService;

    //커뮤니티 게시글 댓글 등록
    @PostMapping("/{comuPostCode}/cmt")
    public ResponseEntity<Void> createComuCmt(
            @PathVariable("comuPostCode") Long comuPostCode, @RequestBody ComuCmtCreateDTO comuCmtCreateDTO
    ) {
        Long currentUserCode = getCurrentUserCode();
        Long comuCmtCode = comuCmtService.createComuCmt(comuPostCode, currentUserCode, comuCmtCreateDTO);
        return ResponseEntity.created(URI.create("/comu-post/postlist/" + comuPostCode + "/cmt/" + comuCmtCode)).build();
    }


    // 현재 로그인 유저 ID(UserCode) 가져오는 메소드
    private Long getCurrentUserCode() {
        return SecurityUtil.getCurrentUserCode();  // SecurityUtil로부터 현재 로그인한 사용자 ID 가져오기
    }
}