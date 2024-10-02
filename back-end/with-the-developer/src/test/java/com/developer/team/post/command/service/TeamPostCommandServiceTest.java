package com.developer.team.post.command.service;

import com.developer.common.exception.CustomException;
import com.developer.common.exception.ErrorCode;
import com.developer.team.post.command.dto.TeamPostDeleteDTO;
import com.developer.team.post.command.dto.TeamPostRegistDTO;
import com.developer.team.post.command.dto.TeamPostUpdateDTO;
import com.developer.team.post.query.dto.TeamPostDTO;
import com.developer.team.post.query.mapper.TeamPostMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.text.ParseException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TeamPostCommandServiceTest {

    @Autowired
    private TeamPostMapper mapper;

    @Autowired
    private TeamPostCommandService teamPostCommandService;

    private static Long postCode;
    private static Long userCode = 1L;

    @Test
    @Order(1)
    @DisplayName("팀 모집 게시글을 등록할 수 있다.")
    void teamPostRegist() throws ParseException {
        // given
        TeamPostRegistDTO registDTO = TeamPostRegistDTO.builder()
                .teamPostTitle("testTitle")
                .teamContent("testContent")
                .teamPostDeadLine("2024-10-04 00:00:00")
                .jobTagNames(List.of(new String[]{"백엔드"}))
                .userCode(userCode)
                .build();
        // when
        postCode = teamPostCommandService.registTeamPost(registDTO);

        // then
        TeamPostDTO registPost = mapper.selectByTeamPostCode(postCode);
        assertEquals(registPost.getTeamPostTitle(), registDTO.getTeamPostTitle());
        assertEquals(registPost.getTeamContent(), registDTO.getTeamContent());
        assertEquals(registPost.getTeamDeadline(), registDTO.getTeamPostDeadLine());
        assertEquals(registPost.getJobTagNames(), registDTO.getJobTagNames());
    }

    @Test
    @Order(2)
    @DisplayName("자신이 작성 한 팀 모집 게시글을 수정할 수 있다.")
    void teamPostUpdate() throws ParseException {

        // given
        TeamPostUpdateDTO teamPostUpdateDTO = TeamPostUpdateDTO.builder()
                .teamPostCode(postCode)
                .teamPostTitle("updateTitle")
                .teamContent("updateContent")
                .teamPostDeadLine("2024-10-05 00:00:00")
                .jobTagNames(List.of(new String[]{"백엔드", "프론트엔드"}))
                .userCode(userCode)
                .build();

        // when
        teamPostCommandService.updateTeamPost(teamPostUpdateDTO);

        // then
        TeamPostDTO updatePost = mapper.selectByTeamPostCode(postCode);
        assertEquals(updatePost.getTeamPostTitle(), teamPostUpdateDTO.getTeamPostTitle());
        assertEquals(updatePost.getTeamContent(), teamPostUpdateDTO.getTeamContent());
        assertEquals(updatePost.getTeamDeadline(), teamPostUpdateDTO.getTeamPostDeadLine());
        assertEquals(updatePost.getJobTagNames(), teamPostUpdateDTO.getJobTagNames());
    }

    @Test
    @Order(3)
    @DisplayName("자신이 작성하지 않은 팀 모집 게시글은 수정할 수 없다.")
    void unauthorizedUserUpdate() {

        // given
        TeamPostUpdateDTO teamPostUpdateDTO = TeamPostUpdateDTO.builder()
                .teamPostCode(postCode)
                .teamPostTitle("updateTitle")
                .teamContent("updateContent")
                .teamPostDeadLine("2024-10-05 00:00:00")
                .jobTagNames(List.of(new String[]{"프론트엔드","백엔드"}))
                .userCode(2L)
                .build();

        // when
        CustomException exception = assertThrows(
                CustomException.class, () -> {
                    teamPostCommandService.updateTeamPost(teamPostUpdateDTO);
                }
        );

        // then
        assertEquals(ErrorCode.UNAUTHORIZED_USER, exception.getErrorCode());
    }

    @Test
    @Order(4)
    @DisplayName("자신이 작성하지 않은 팀 모집 게시글은 삭제할 수 없다.")
    void unauthorizedUserDelete() throws ParseException {
        // given
        TeamPostDeleteDTO teamPostDeleteDTO = new TeamPostDeleteDTO(postCode, 2L);

        // when
        CustomException exception = assertThrows(
                CustomException.class, () -> {
                    teamPostCommandService.deleteTeamPost(teamPostDeleteDTO);
                }
        );

        // then
        assertEquals(ErrorCode.UNAUTHORIZED_USER, exception.getErrorCode());
    }

    @Test
    @Order(5)
    @DisplayName("자신이 작성한 팀 모집 게시글을 삭제할 수 있다.")
    void deleteTeamPost() throws ParseException {

        // given
        TeamPostDeleteDTO teamPostDeleteDTO = new TeamPostDeleteDTO(postCode, userCode);

        // when
        teamPostCommandService.deleteTeamPost(teamPostDeleteDTO);

        // then
        TeamPostDTO deletePost = mapper.selectByTeamPostCode(postCode);
        assertNull(deletePost);
    }



}