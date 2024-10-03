package com.developer.team.comment.command.controller;

import com.developer.client.UserServiceClient;
import com.developer.common.success.SuccessCode;
import com.developer.common.util.BaseEntity;
import com.developer.config.TokenDecoder;
import com.developer.team.comment.command.dto.TeamCmtRegistDTO;
import com.developer.team.comment.command.dto.TeamCmtUpdateDTO;
import com.developer.team.comment.command.service.TeamCmtCommandService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/team")
@Slf4j
@RequiredArgsConstructor
public class TeamCmtCommandController extends BaseEntity {

    private final TeamCmtCommandService teamCmtCommandService;
    private final TokenDecoder tokenDecoder;
    private final UserServiceClient userServiceClient;

    @PostMapping("/cmt")
    public ResponseEntity<SuccessCode> teamCmtRegist(
            @RequestHeader("Authorization") String token,
            @RequestBody TeamCmtRegistDTO teamCmtRegistDTO
    ){
        Long loginUser = tokenDecoder.getUserCodeFromToken(token);

        userServiceClient.userCheck(loginUser);

        teamCmtRegistDTO.setUserCode(loginUser);

        teamCmtCommandService.registTeamCmt(teamCmtRegistDTO);

        return ResponseEntity.ok(SuccessCode.TEAM_COMMENT_CREATE_OK);

    }

    @PutMapping("/cmt/{teamCmtCode}")
    public ResponseEntity<SuccessCode> teamCmtUpdate(
            @RequestHeader("Authorization") String token,
            @PathVariable("teamCmtCode") Long teamCmtCode,
            @RequestBody TeamCmtUpdateDTO teamCmtUpdateDTO
    ){
        Long loginUser = tokenDecoder.getUserCodeFromToken(token);

        userServiceClient.userCheck(loginUser);

        teamCmtUpdateDTO.setUserCode(loginUser);

        teamCmtCommandService.updateTeamCmt(teamCmtUpdateDTO, teamCmtCode);

        return ResponseEntity.ok(SuccessCode.TEAM_COMMENT_UPDATE_OK);
    }

    @DeleteMapping("/cmt/{teamCmtCode}")
    public ResponseEntity<SuccessCode> teamCmtDelete(
            @RequestHeader("Authorization") String token,
            @PathVariable("teamCmtCode") Long teamCmtCode
    ){

        Long loginUser = tokenDecoder.getUserCodeFromToken(token);

        teamCmtCommandService.deleteTeamCmt(teamCmtCode, loginUser);

        return ResponseEntity.ok(SuccessCode.TEAM_COMMENT_DELETE_OK);
    }

}
