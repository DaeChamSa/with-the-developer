package com.developer.team.comment.command.service;

import com.developer.common.exception.CustomException;
import com.developer.common.exception.ErrorCode;
import com.developer.team.comment.command.dto.TeamCmtRegistDTO;
import com.developer.team.comment.command.dto.TeamCmtUpdateDTO;
import com.developer.team.comment.query.dto.ResponseTeamCmtListDTO;
import com.developer.team.comment.query.mapper.TeamCmtMapper;
import com.developer.team.post.command.dto.TeamPostDeleteDTO;
import com.developer.team.post.command.dto.TeamPostRegistDTO;
import com.developer.team.post.command.service.TeamPostCommandService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
// 테스트 순서 보장
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TeamCmtCommandServiceTest {

    private static Long postCode;

    private static final Long userCode = 1L;

    private static Long cmtCode;

    private static Map<String, Object> params;

    @Autowired
    private TeamPostCommandService teamPostCommandService;

    @Autowired
    private TeamCmtCommandService teamCmtCommandService;

    @Autowired
    private TeamCmtMapper teamCmtMapper;

    @Test
    @Order(1)
    @DisplayName("팀 모집 게시글에 댓글을 작성할 수 있다.")
    void registerTeamCmt() throws ParseException {

        // given
        // 테스트용 게시글 삽입
        TeamPostRegistDTO registPostDTO = TeamPostRegistDTO.builder()
                .teamPostTitle("testTitle1")
                .teamContent("testContent1")
                .teamPostDeadLine("2024-10-04 00:00:00")
                .jobTagNames(List.of(new String[]{"백엔드"}))
                .userCode(userCode)
                .build();

        postCode = teamPostCommandService.registTeamPost(registPostDTO);

        // 댓글 조회를 위한 Map 생성
        params = new HashMap<>();
        params.put("teamPostCode", postCode);
        params.put("offset", 0);

        // 댓글 등록
        TeamCmtRegistDTO registCommentDTO = TeamCmtRegistDTO.builder()
                .teamCmt("testComment")
                .teamPostCode(postCode)
                .userCode(userCode)
                .build();

        // when
        cmtCode = teamCmtCommandService.registTeamCmt(registCommentDTO);

        // then
        List<ResponseTeamCmtListDTO> teamCmtList = teamCmtMapper.selectTeamCmtList(params);
        ResponseTeamCmtListDTO teamCmtDTO = teamCmtList.get(0);

        assertNotNull(teamCmtList);
        assertEquals(1, teamCmtList.size());
        assertEquals(registCommentDTO.getTeamCmt(), teamCmtDTO.getTeamCmt());

    }

    @Test
    @Order(2)
    @DisplayName("자신이 작성한 팀 모집 게시글 댓글을 수정할 수 있다.")
    void updateTeamCmt() {

        // given
        TeamCmtUpdateDTO teamCmtUpdateDTO = TeamCmtUpdateDTO.builder()
                .teamCmt("updateComment")
                .userCode(userCode)
                .build();

        // when
        teamCmtCommandService.updateTeamCmt(teamCmtUpdateDTO, cmtCode);

        // then
        List<ResponseTeamCmtListDTO> teamCmtList = teamCmtMapper.selectTeamCmtList(params);
        ResponseTeamCmtListDTO teamCmtDTO = teamCmtList.get(0);

        assertEquals(teamCmtUpdateDTO.getTeamCmt(), teamCmtDTO.getTeamCmt());
    }

    @Test
    @Order(3)
    @DisplayName("자신이 작성하지 않은 팀 모집 게시글 댓글은 수정할 수 없다.")
    void unauthorizedUpdate() {
        // given
        TeamCmtUpdateDTO teamCmtUpdateDTO = TeamCmtUpdateDTO.builder()
                .teamCmt("updateComment")
                .userCode(2L)
                .build();

        // when
        CustomException exception = assertThrows(
                CustomException.class, () -> {
                    teamCmtCommandService.updateTeamCmt(teamCmtUpdateDTO, cmtCode);
                }
        );

        // then
        assertEquals(ErrorCode.UNAUTHORIZED_USER_COMMENT, exception.getErrorCode());
    }

    @Test
    @Order(4)
    @DisplayName("자신이 작성하지 않은 팀 모집 게시글 댓글은 삭제할 수 없다.")
    void unauthorizedCmt(){

        // given

        // when
        CustomException exception = assertThrows(
                CustomException.class, () -> {
                    teamCmtCommandService.deleteTeamCmt(cmtCode, 2L);
                }
        );

        // then
        assertEquals(ErrorCode.UNAUTHORIZED_USER_COMMENT, exception.getErrorCode());
    }

    @Test
    @Order(5)
    @DisplayName("자신이 작성한 팀 모집 게시글 댓글을 삭제할 수 있다.")
    void deleteTeamCmt() throws ParseException {

        // given

        // when
        teamCmtCommandService.deleteTeamCmt(cmtCode, userCode);

        // then
        assertEquals(0,teamCmtMapper.selectTeamCmtList(params).size());
        teamPostCommandService.deleteTeamPost(new TeamPostDeleteDTO(postCode, userCode));
    }
}