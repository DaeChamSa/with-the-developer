package com.developer.team.post.query.service;

import com.developer.team.post.command.dto.TeamPostDeleteDTO;
import com.developer.team.post.command.dto.TeamPostRegistDTO;
import com.developer.team.post.command.service.TeamPostCommandService;
import com.developer.team.post.query.dto.TeamPostDTO;
import com.developer.team.post.query.dto.TeamPostListDTO;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class TeamPostQueryServiceTest {

    @Autowired
    private TeamPostQueryService queryService;

    @Autowired
    private TeamPostCommandService commandService;

    private static Long postCode1;
    private static Long postCode2;
    private static TeamPostRegistDTO registDTO1;

    // 조회 테스트에 사용될 테스트 데이터 입력
    @BeforeEach
    public void setUp() throws Exception {

        registDTO1 = TeamPostRegistDTO.builder()
                .teamPostTitle("testTitle1")
                .teamContent("testContent1")
                .teamPostDeadLine("2024-10-04 00:00:00")
                .jobTagNames(List.of(new String[]{"백엔드"}))
                .userCode(1L)
                .build();
        TeamPostRegistDTO registDTO2 = TeamPostRegistDTO.builder()
                .teamPostTitle("testTitle2")
                .teamContent("testContent2")
                .teamPostDeadLine("2024-10-04 00:00:00")
                .jobTagNames(List.of(new String[]{"백엔드", "프론트엔드"}))
                .userCode(1L)
                .build();

        postCode1 = commandService.registTeamPost(registDTO1);
        postCode2 = commandService.registTeamPost(registDTO2);
    }

    // 테스트용 게시글 삭제
    @AfterEach
    public void destroy() throws Exception {

        commandService.deleteTeamPost(new TeamPostDeleteDTO(postCode1, 1L));
        commandService.deleteTeamPost(new TeamPostDeleteDTO(postCode2, 1L));
    }

    @Test
    @DisplayName("팀 모집 게시글 목록을 조회할 수 있다.")
    void selectAllTeamPost() {

        // given

        // when
        List<TeamPostListDTO> teamPosts = queryService.selectAllTeamPost(1);

        // then
        assertEquals(2, teamPosts.size());
    }

    @Test
    @DisplayName("팀 모집 상세 내용을 조회할 수 있다.")
    void selectTeamPostById() {

        // given

        // when
        TeamPostDTO selectedPost = queryService.selectByTeamPostCode(postCode1);

        // then
        assertEquals(registDTO1.getTeamPostTitle(), selectedPost.getTeamPostTitle());
        assertEquals(registDTO1.getTeamContent(), selectedPost.getTeamContent());
        assertEquals(registDTO1.getTeamPostDeadLine(), selectedPost.getTeamDeadline());
        assertEquals(registDTO1.getJobTagNames(), selectedPost.getJobTagNames());
    }

}