package com.developer.comu.comment.query.controller;

import com.developer.comu.comment.query.dto.ComuCmtDTO;
import com.developer.comu.comment.query.service.ComuCmtQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/comu-post")
@RestController
public class ComuCmtQueryController {

    private final ComuCmtQueryService comuCmtQueryService;

    // 커뮤니티 게시글 댓글 조회
    @GetMapping("/postlist/{comuPostCode}/cmtlist")
    public List<ComuCmtDTO> getComuCmtList(@PathVariable(name = "comuPostCode") Long comuPostCode, @RequestParam(defaultValue = "1")int page) {
        List<ComuCmtDTO> comuCmtList = comuCmtQueryService.getComuCmtListByPostCode(comuPostCode, page);

        return comuCmtList;
    }
}
