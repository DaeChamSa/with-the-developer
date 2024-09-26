package com.developer.comu.comment.query.controller;

import com.developer.comu.comment.query.dto.ComuCmtDTO;
import com.developer.comu.comment.query.service.ComuCmtQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/comu-post")
public class ComuCmtQueryController {

    private final ComuCmtQueryService comuCmtQueryService;

    // 커뮤니티 게시글 댓글 조회
    @GetMapping("/postlist/{comuPostCode}/cmt")
    public List<ComuCmtDTO> getComuCmtList(@PathVariable Long comuPostCode, @RequestParam(defaultValue = "1")int page) {
        List<ComuCmtDTO> comuCmtList = comuCmtQueryService.getComuCmtListByPostCode(comuPostCode, page);

        return comuCmtList;
    }
}
