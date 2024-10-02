package com.developer.team.comment.query.controller;

import com.developer.team.comment.query.dto.ResponseTeamCmtListDTO;
import com.developer.team.comment.query.service.TeamCmtQueryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "team-comment", description = "팀모집 댓글 API")
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/team")
public class TeamCmtQueryController {

    private final TeamCmtQueryService teamCmtQueryService;

    @GetMapping("/cmt/{teamPostCode}")
    public ResponseEntity<List<ResponseTeamCmtListDTO>> selectTeamCmtList(
            @PathVariable("teamPostCode") Long teamPostCode,
            @RequestParam(name = "page", defaultValue = "1") Integer page
    ) {

        List<ResponseTeamCmtListDTO> teamCmtList = teamCmtQueryService.selectTeamCmtList(teamPostCode, page);

        return ResponseEntity.ok(teamCmtList);
    }

}
