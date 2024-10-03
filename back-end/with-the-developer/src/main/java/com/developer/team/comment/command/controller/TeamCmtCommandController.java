package com.developer.team.comment.command.controller;

import com.developer.common.success.SuccessCode;
import com.developer.common.util.BaseEntity;
import com.developer.team.comment.command.dto.TeamCmtRegistDTO;
import com.developer.team.comment.command.dto.TeamCmtUpdateDTO;
import com.developer.team.comment.command.service.TeamCmtCommandService;
import com.developer.user.security.SecurityUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "team-comment", description = "팀모집 댓글 API")
@RestController
@RequestMapping("/team")
@Slf4j
@RequiredArgsConstructor
public class TeamCmtCommandController extends BaseEntity {

    private final TeamCmtCommandService teamCmtCommandService;

    @PostMapping("/cmt")
    @Operation(summary = "팀모집 게시글 댓글 등록", description = "등록되어 있는 팀모집 게시글에 새로운 댓글을 등록합니다.")
    public ResponseEntity<SuccessCode> teamCmtRegist(@RequestBody TeamCmtRegistDTO teamCmtRegistDTO){

        Long loginUser = SecurityUtil.getCurrentUserCode();

        teamCmtRegistDTO.setUserCode(loginUser);

        teamCmtCommandService.registTeamCmt(teamCmtRegistDTO);

        return ResponseEntity.ok(SuccessCode.TEAM_COMMENT_CREATE_OK);

    }

    @PutMapping("/cmt/{teamCmtCode}")
    @Operation(summary = "팀모집 게시글 댓글 수정", description = "등록되어 있는 팀모집 게시글에 등록된 본인의 댓글을 수정합니다.")
    public ResponseEntity<SuccessCode> teamCmtUpdate(
            @PathVariable("teamCmtCode") Long teamCmtCode,
            @RequestBody TeamCmtUpdateDTO teamCmtUpdateDTO
    ){
        Long loginUser = SecurityUtil.getCurrentUserCode();

        teamCmtUpdateDTO.setUserCode(loginUser);

        teamCmtCommandService.updateTeamCmt(teamCmtUpdateDTO, teamCmtCode);

        return ResponseEntity.ok(SuccessCode.TEAM_COMMENT_UPDATE_OK);
    }

    @DeleteMapping("/cmt/{teamCmtCode}")
    @Operation(summary = "팀모집 게시글 댓글 삭제", description = "등록되어 있는 팀모집 게시글에 등록된 본인의 댓글을 삭제합니다.")
    public ResponseEntity<SuccessCode> teamCmtDelete(@PathVariable("teamCmtCode") Long teamCmtCode){

        Long loginUser = SecurityUtil.getCurrentUserCode();

        teamCmtCommandService.deleteTeamCmt(teamCmtCode, loginUser);

        return ResponseEntity.ok(SuccessCode.TEAM_COMMENT_DELETE_OK);
    }

}
