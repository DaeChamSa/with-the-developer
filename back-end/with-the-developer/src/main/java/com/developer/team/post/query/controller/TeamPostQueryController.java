package com.developer.team.post.query.controller;

import com.developer.team.post.query.dto.TeamPostDTO;
import com.developer.team.post.query.dto.TeamPostListDTO;
import com.developer.team.post.query.service.TeamPostQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/team")
@Slf4j
@RequiredArgsConstructor
public class TeamPostQueryController {

    private final TeamPostQueryService teamPostQueryService;

    // 팀 모집 게시글 코드로 상세 조회
    @GetMapping("/detail/{teamPostCode}")
    public ResponseEntity<TeamPostDTO> teamPostDetail(@PathVariable(name = "teamPostCode") Long teamPostCode) {
        TeamPostDTO foundTeamPost = teamPostQueryService.selectByTeamPostCode(teamPostCode);

        return ResponseEntity.ok(foundTeamPost);
    }

    // 팀 모집 게시글 모두 조회(페이징 처리)
    @GetMapping("/postlist")
    public ResponseEntity<List<TeamPostListDTO>> teamPostList( @RequestParam(name = "page", defaultValue = "1") Integer page ) {
        List<TeamPostListDTO> teamPostList = teamPostQueryService.selectAllTeamPost(page);

        return ResponseEntity.ok(teamPostList);
    }

    @GetMapping("/search/tag")
    public ResponseEntity<List<TeamPostListDTO>> teamPostSearch (
            @RequestParam("searchTags") List<String> searchTags,
            @RequestParam(name = "page", defaultValue = "1") Integer page
    ) {
        List<TeamPostListDTO> serachList = teamPostQueryService.selectByTags(searchTags, page);

        return ResponseEntity.ok(serachList);
    }
}
