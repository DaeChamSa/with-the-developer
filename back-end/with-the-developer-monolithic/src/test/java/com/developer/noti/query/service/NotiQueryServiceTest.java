package com.developer.noti.query.service;

import com.developer.noti.command.application.dto.NotiCommentCreateDTO;
import com.developer.noti.command.application.service.NotiCommandService;
import com.developer.noti.command.domain.aggregate.PostType;
import com.developer.noti.query.dto.ResponseNotiDTO;
import com.developer.team.post.command.dto.TeamPostRegistDTO;
import com.developer.team.post.command.service.TeamPostCommandService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class NotiQueryServiceTest {

    @Autowired
    private NotiQueryService notiQueryService;

    @Autowired
    private NotiCommandService notiCommandService;

    @Autowired
    private TeamPostCommandService teamPostCommandService;

    private Long userCode = 1L;

    Long notiCode1;
    Long notiCode2;
    Long notiCode3;
    Long notiCode4;

    @BeforeEach
    public void setUp() throws ParseException {
        // given
        TeamPostRegistDTO registDTO = TeamPostRegistDTO.builder()
                .teamPostTitle("testTitle")
                .teamContent("testContent")
                .teamPostDeadLine("2024-10-04 00:00:00")
                .jobTagNames(List.of(new String[]{"백엔드"}))
                .userCode(userCode)
                .build();

        Long postCode = teamPostCommandService.registTeamPost(registDTO);

        // 알림 생성
        NotiCommentCreateDTO notiCommentCreateDTO = new NotiCommentCreateDTO();
        notiCommentCreateDTO.setPostCode(postCode);
        notiCommentCreateDTO.setUserCode(userCode);
        notiCommentCreateDTO.setPostType(PostType.TEAM);

        notiCode1 = notiCommandService.addCommentEvent(notiCommentCreateDTO);
        notiCode2 = notiCommandService.addCommentEvent(notiCommentCreateDTO);
        notiCode3 = notiCommandService.addCommentEvent(notiCommentCreateDTO);
        notiCode4 = notiCommandService.addCommentEvent(notiCommentCreateDTO);

        notiCommandService.readNoti(userCode, notiCode1);
        System.out.println("더미 데이터 삽입");

    }

    @AfterEach
    public void tearDown() {
        notiCommandService.deleteNoti(userCode, notiCode1);
        notiCommandService.deleteNoti(userCode, notiCode2);
        notiCommandService.deleteNoti(userCode, notiCode3);
        notiCommandService.deleteNoti(userCode, notiCode4);
    }

    @Test
    @DisplayName("유저의 삭제되지 않은 모든 알림 목록을 조회할 수 있다.")
    void selectAllNotiTest(){

        // given

        // when
        List<ResponseNotiDTO> notiList = notiQueryService.findAllByUserCode(userCode);

        // then
        assertNotNull(notiList);
        assertEquals(4, notiList.size());
    }

    @Test
    @DisplayName("유저의 읽음 처리 된 모든 알림 목록을 조회할 수 있다.")
    void selectReadNotiTest(){

        // given

        // when
        List<ResponseNotiDTO> notiList = notiQueryService.findByIsRead(userCode);

        // then
        assertNotNull(notiList);
        assertEquals(1, notiList.size());
    }

    @Test
    @DisplayName("유저의 읽지 않은 모든 알림 목록을 조회할 수 있다.")
    void selectNotReadNotiTest(){

        // given

        // when
        List<ResponseNotiDTO> notiList = notiQueryService.findByNotRead(userCode);

        // then
        assertNotNull(notiList);
        assertEquals(3, notiList.size());
    }
}