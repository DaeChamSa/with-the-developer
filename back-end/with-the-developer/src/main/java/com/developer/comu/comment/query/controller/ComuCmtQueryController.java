package com.developer.comu.comment.query.controller;

import com.developer.comu.comment.query.dto.ComuCmtDTO;
import com.developer.comu.comment.query.service.ComuCmtQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "community-comment", description = "커뮤니티 댓글 API")
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/comu-post")
@RestController
public class ComuCmtQueryController {

    private final ComuCmtQueryService comuCmtQueryService;

    // 커뮤니티 게시글 댓글 조회
    @GetMapping("/postlist/{comuPostCode}/cmtlist")
    @Operation(summary = "커뮤니티 게시글 댓글 조회", description = "커뮤니티 게시글에 등록되어 있는 댓글을 조회합니다.")
    public List<ComuCmtDTO> getComuCmtList(@PathVariable(name = "comuPostCode") Long comuPostCode, @RequestParam(defaultValue = "1")int page) {
        List<ComuCmtDTO> comuCmtList = comuCmtQueryService.getComuCmtListByPostCode(comuPostCode, page);

        return comuCmtList;
    }
}
