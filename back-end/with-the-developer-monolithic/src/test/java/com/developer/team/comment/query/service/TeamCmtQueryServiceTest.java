package com.developer.team.comment.query.service;

import com.developer.team.comment.command.dto.TeamCmtRegistDTO;
import com.developer.team.comment.command.service.TeamCmtCommandService;
import com.developer.team.comment.query.dto.ResponseTeamCmtListDTO;
import com.developer.team.post.command.dto.TeamPostDeleteDTO;
import com.developer.team.post.command.dto.TeamPostRegistDTO;
import com.developer.team.post.command.service.TeamPostCommandService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TeamCmtQueryServiceTest {

    @Autowired
    private TeamCmtQueryService teamCmtQueryService;

    @Autowired
    private TeamCmtCommandService teamCmtCommandService;

    @Autowired
    private TeamPostCommandService teamPostCommandService;

    private static Long postCode;

    private static Long cmtCode1;
    private static Long cmtCode2;
    private static Long cmtCode3;

    @BeforeEach
    void setUp() throws ParseException {
        // 테스트용 게시글, 댓글 삽입
        TeamPostRegistDTO registPostDTO = TeamPostRegistDTO.builder()
                .teamPostTitle("testTitle1")
                .teamContent("testContent1")
                .teamPostDeadLine("2024-10-04 00:00:00")
                .jobTagNames(List.of(new String[]{"백엔드"}))
                .userCode(1L)
                .build();

        postCode = teamPostCommandService.registTeamPost(registPostDTO);

        TeamCmtRegistDTO registCommentDTO1 = TeamCmtRegistDTO.builder()
                .teamCmt("testComment")
                .teamPostCode(postCode)
                .userCode(1L)
                .build();

        TeamCmtRegistDTO registCommentDTO2 = TeamCmtRegistDTO.builder()
                .teamCmt("testComment")
                .teamPostCode(postCode)
                .userCode(1L)
                .build();

        TeamCmtRegistDTO registCommentDTO3 = TeamCmtRegistDTO.builder()
                .teamCmt("testComment")
                .teamPostCode(postCode)
                .userCode(1L)
                .build();

        cmtCode1 = teamCmtCommandService.registTeamCmt(registCommentDTO1);
        cmtCode2 = teamCmtCommandService.registTeamCmt(registCommentDTO2);
        cmtCode3 = teamCmtCommandService.registTeamCmt(registCommentDTO3);

    }
    @AfterEach
    void destroy() throws ParseException {
        // 테스트 시 사용된 데이터 삭제 처리
        teamCmtCommandService.deleteTeamCmt(cmtCode1, 1L);
        teamCmtCommandService.deleteTeamCmt(cmtCode2, 1L);
        teamCmtCommandService.deleteTeamCmt(cmtCode3, 1L);
        teamPostCommandService.deleteTeamPost(new TeamPostDeleteDTO(postCode, 1L));
    }

    @Test
    @DisplayName("팀 게시글의 댓글 목록을 조회할 수 있다.")
    void getTeamCmtList() {

        // given

        // when
        List<ResponseTeamCmtListDTO> cmtList = teamCmtQueryService.selectTeamCmtList(postCode, 1);

        // then
        assertNotNull(cmtList);
        assertEquals(3, cmtList.size());
    }

}