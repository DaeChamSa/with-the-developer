package com.developer.team.post.query.controller;

import com.developer.search.query.dto.SearchResultDTO;
import com.developer.search.query.service.SearchService;
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
    private final SearchService searchService;

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
            @RequestParam("searchTag") String searchTag,
            @RequestParam(name = "page", defaultValue = "1") Integer page
    ) {
        log.info(searchTag.toString());
        List<TeamPostListDTO> serachList = teamPostQueryService.selectByTags(searchTag, page);

        return ResponseEntity.ok(serachList);
    }

    // 팀모집 게시판 내에서 검색하기
    @GetMapping("/search")
    public ResponseEntity<List<SearchResultDTO>> searchTeamPost(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "1") Integer page)
    {
        List<SearchResultDTO> teamPostSearchResultDTO = searchService.search("team", keyword, page);
        return ResponseEntity.ok(teamPostSearchResultDTO);
    }
}
