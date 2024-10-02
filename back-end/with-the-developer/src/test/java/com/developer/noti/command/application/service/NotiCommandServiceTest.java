package com.developer.noti.command.application.service;

import com.developer.common.exception.CustomException;
import com.developer.common.exception.ErrorCode;
import com.developer.msg.command.application.dto.MessageRequestDTO;
import com.developer.msg.command.application.service.MessageCommandService;
import com.developer.msg.command.domain.aggregate.ResMsg;
import com.developer.msg.query.mapper.MessageMapper;
import com.developer.noti.command.application.dto.NotiCommentCreateDTO;
import com.developer.noti.command.application.dto.NotiMsgCreateDTO;
import com.developer.noti.command.domain.aggregate.Noti;
import com.developer.noti.command.domain.aggregate.PostType;
import com.developer.noti.command.domain.repository.NotiRepository;
import com.developer.noti.query.dto.ResponseNotiDTO;
import com.developer.noti.query.mapper.NotiMapper;
import com.developer.team.post.command.dto.TeamPostRegistDTO;
import com.developer.team.post.command.service.TeamPostCommandService;
import com.developer.user.command.application.dto.RegisterUserDTO;
import com.developer.user.command.application.service.UserCommandService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class NotiCommandServiceTest {

    @Autowired
    private NotiCommandService notiCommandService;

    @Autowired
    private NotiRepository notiRepository;

    @Autowired
    private NotiMapper notiMapper;

    @Autowired
    private MessageMapper messageMapper;

    @Autowired
    private TeamPostCommandService teamPostCommandService;

    @Autowired
    private UserCommandService userCommandService;

    @Autowired
    private MessageCommandService messageCommandService;

    private static Long userCode1 = 1L;

    private static Long postCode;

    private static Long notiCode;

    private static Long userCode2;

    @Test
    @Order(1)
    @DisplayName("회원의 게시글 마다 댓글 알림울 등록할 수 있다.")
    void commentNotiCreateTest() throws ParseException {

        // given
        TeamPostRegistDTO registDTO = TeamPostRegistDTO.builder()
                .teamPostTitle("testTitle")
                .teamContent("testContent")
                .teamPostDeadLine("2024-10-04 00:00:00")
                .jobTagNames(List.of(new String[]{"백엔드"}))
                .userCode(userCode1)
                .build();

        postCode = teamPostCommandService.registTeamPost(registDTO);

        // 알림 생성
        NotiCommentCreateDTO notiCommentCreateDTO = new NotiCommentCreateDTO();
        notiCommentCreateDTO.setPostCode(postCode);
        notiCommentCreateDTO.setUserCode(userCode1);
        notiCommentCreateDTO.setPostType(PostType.TEAM);

        // when
        notiCode = notiCommandService.addCommentEvent(notiCommentCreateDTO);

        // then
        List<ResponseNotiDTO> notiList = notiMapper.findByNotRead(userCode1);
        ResponseNotiDTO responseNotiDTO = notiList.get(0);

        assertNotNull(notiList);
        assertNotNull(responseNotiDTO);
        assertEquals(1, notiList.size());
        assertEquals("새로운 댓글이 달렸습니다.", responseNotiDTO.getNotiTitle());
    }

    @Test
    @Order(2)
    @DisplayName("생성 된 알림을 읽음 처리할 수 있다.")
    void notiReadTest() throws ParseException {

        // given

        // when
        notiCommandService.readNoti(userCode1, notiCode);

        // then
        Noti noti = notiRepository.findByNotiCodeAndUserCode(notiCode, userCode1).orElseThrow(
                () -> new CustomException(ErrorCode.NOT_FOUND_NOTI)
        );
        assertEquals(0, notiMapper.findByNotRead(userCode1).size());
        assertEquals(1, notiMapper.findByIsRead(userCode1).size());
        assertTrue(noti.isNotiRead());
    }

    @Test
    @Order(3)
    @DisplayName("생성 된 알림을 삭제 처리할 수 있다.")
    void notiDeleteTest() throws ParseException {

        // given

        // when
        notiCommandService.deleteNoti(userCode1, notiCode);

        // then
        Noti noti = notiRepository.findByNotiCodeAndUserCode(notiCode, userCode1).orElseThrow(
                () -> new CustomException(ErrorCode.NOT_FOUND_NOTI)
        );
        assertEquals(0, notiMapper.findAllNotNotiDelStatus(userCode1).size());
        assertTrue(noti.isNotiDelStatus());
    }

    @Test
    @Order(4)
    @DisplayName("회원 마다 쪽지 등록 시 알림을 등록할 수 있다.")
    @Transactional
    void msgNotiCreateTest() throws ParseException {

        // then
        RegisterUserDTO registerUserDTO = new RegisterUserDTO();
        registerUserDTO.setUserId("responseMsgId");
        registerUserDTO.setUserName("responseUserName");
        registerUserDTO.setUserBirth("2000-11-11");
        registerUserDTO.setUserEmail("responseUserEmail");
        registerUserDTO.setUserPhone("01073437303");
        registerUserDTO.setUserNick("responseUserNick");
        registerUserDTO.setUserPw("responseUserPw");

        userCode2 = userCommandService.registerUser(registerUserDTO);

        MessageRequestDTO messageRequestDTO = new MessageRequestDTO();
        messageRequestDTO.setMsgContent("testContent");
        messageRequestDTO.setRecipientUserCode(userCode2);

        Long messageCode = messageCommandService.sendMessage(messageRequestDTO,userCode1);

        NotiMsgCreateDTO notiMsgCreateDTO = new NotiMsgCreateDTO();
        notiMsgCreateDTO.setMsgCode(messageCode);
        notiMsgCreateDTO.setReqUserCode(userCode1);
        notiMsgCreateDTO.setResUserCode(userCode2);

        // when
        notiCommandService.addMsgEvent(notiMsgCreateDTO);

        // then
        List<ResponseNotiDTO> notiList = notiMapper.findAllNotNotiDelStatus(userCode2);
        ResponseNotiDTO responseNotiDTO = notiList.get(0);
        assertEquals(1, notiList.size());
        assertEquals("새로운 쪽지가 있습니다.", responseNotiDTO.getNotiTitle());
    }
}