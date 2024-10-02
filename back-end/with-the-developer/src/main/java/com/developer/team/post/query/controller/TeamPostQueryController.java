package com.developer.team.post.query.controller;

import com.developer.search.query.dto.SearchResultDTO;
import com.developer.search.query.service.SearchService;
import com.developer.team.post.query.dto.TeamPostDTO;
import com.developer.team.post.query.dto.TeamPostListDTO;
import com.developer.team.post.query.service.TeamPostQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "team-post", description = "팀모집 게시물 API")
@RestController
@RequestMapping("/team")
@Slf4j
@RequiredArgsConstructor
public class TeamPostQueryController {

    private final TeamPostQueryService teamPostQueryService;
    private final SearchService searchService;

    // 팀 모집 게시글 코드로 상세 조회
    @GetMapping("/detail/{teamPostCode}")
    @Operation(summary = "팀모집 게시글 상세 조회", description = "등록되어 있는 팀모집 게시글의 상세 내용을 조회합니다.")
    public ResponseEntity<TeamPostDTO> teamPostDetail(@PathVariable(name = "teamPostCode") Long teamPostCode) {
        TeamPostDTO foundTeamPost = teamPostQueryService.selectByTeamPostCode(teamPostCode);

        return ResponseEntity.ok(foundTeamPost);
    }

    // 팀 모집 게시글 모두 조회(페이징 처리)
    @GetMapping("/postlist")
    @Operation(summary = "팀모집 게시글 목록 조회", description = "등록되어 있는 팀모집 게시글 목록을 조회합니다.")
    public ResponseEntity<List<TeamPostListDTO>> teamPostList( @RequestParam(name = "page", defaultValue = "1") Integer page ) {
        List<TeamPostListDTO> teamPostList = teamPostQueryService.selectAllTeamPost(page);

        return ResponseEntity.ok(teamPostList);
    }

    @GetMapping("/search/tag")
    @Operation(summary = "직무태그를 통해 팀모집 게시글 검색", description = "직무태그를 통해 팀모집 게시글을 검색합니다.")
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
    @Operation(summary = "팀모집 게시판 검색", description = "키워드(keyword)를 포함하고 있는 팀모집 게시글을 검색을 통해 조회합니다.")
    public ResponseEntity<List<SearchResultDTO>> searchTeamPost(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "1") Integer page)
    {
        List<SearchResultDTO> teamPostSearchResultDTO = searchService.search("team", keyword, page);
        return ResponseEntity.ok(teamPostSearchResultDTO);
    }
}
