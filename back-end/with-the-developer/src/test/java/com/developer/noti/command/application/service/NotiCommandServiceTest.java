//package com.developer.noti.command.application.service;
//
//import com.developer.dbti.command.domain.aggregate.DbtiRole;
//import com.developer.noti.command.application.dto.NotiCreateDTO;
//import com.developer.noti.command.domain.aggregate.PostType;
//import com.developer.noti.query.dto.AllNotiDTO;
//import com.developer.noti.query.dto.ResponseNotiDTO;
//import com.developer.noti.query.mapper.NotiMapper;
//import com.developer.user.command.dto.RegisterUserDTO;
//import com.developer.user.command.service.UserCommandService;
//import jakarta.transaction.Transactional;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.text.ParseException;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//@Transactional
//class NotiCommandServiceTest {
//
//    private static final Logger log = LoggerFactory.getLogger(NotiCommandServiceTest.class);
//    @Autowired
//    private NotiCommandService notiCommandService;
//
//    @Autowired
//    private UserCommandService userCommandService;
//
//    @Autowired
//    private NotiMapper notiMapper;
//
//    @BeforeEach
//    void setUp() throws ParseException {
////        RegisterUserDTO userDTO = new RegisterUserDTO(
////                "testid1", "testpass1", "test@naver.com",
////                "홍길동", "Hong", "1990-01-01", "010-5555-5378");
////        userCommandService.registerUser(userDTO);
//
//    }
//
//    @Test
//    @DisplayName("알림을 읽음처리 할 수 있다.")
//    void readNoti(){
//        // Given
//        NotiCreateDTO notiCreateDTO = new NotiCreateDTO();
//        notiCreateDTO.setPostCode(2L);
//        notiCreateDTO.setUserCode(6L);
//        notiCreateDTO.setPostType(PostType.TEAM);
//        notiCommandService.addCommentEvent(notiCreateDTO);
//
//        // When
//        notiCommandService.readNoti(6L, 13L);
//
//        // Then
//        ResponseNotiDTO responseNotiDTO = notiMapper.findAllNotNotiDelStatus(6L).get(0);
//        assertTrue(responseNotiDTO.isNotiRead());
//    }
//
//    @Test
//    @DisplayName("알림을 삭제처리 할 수 있다.")
//    void deleteNoti() {
//        // Given
//        NotiCreateDTO notiCreateDTO = new NotiCreateDTO();
//        notiCreateDTO.setPostCode(2L);
//        notiCreateDTO.setUserCode(6L);
//        notiCreateDTO.setPostType(PostType.TEAM);
//        notiCommandService.addCommentEvent(notiCreateDTO);
//
//        // When
//        notiCommandService.deleteNoti(6L, 13L);
//
//        // Then
//        AllNotiDTO allNotiDTO = notiMapper.findByAll(6L).get(1);
//        log.info("넌 뭘까 {}", allNotiDTO.isNotiDelStatus());
//        assertTrue(allNotiDTO.isNotiDelStatus());
//    }
//
//    @Test
//    @DisplayName("댓글 작성시 알림을 발생시킬 수 있다.")
//    void addCommentEvent() {
//        // Given
//        NotiCreateDTO notiCreateDTO = new NotiCreateDTO();
//        notiCreateDTO.setPostCode(2L);
//        notiCreateDTO.setUserCode(6L);
//        notiCreateDTO.setPostType(PostType.TEAM);
//
//        // When
//        notiCommandService.addCommentEvent(notiCreateDTO);
//
//        // Then
//        ResponseNotiDTO responseNotiDTO = notiMapper.findAllNotNotiDelStatus(6L).get(0);
//        assertEquals("/team/detail/2", responseNotiDTO.getNotiUrl());
//        assertEquals("새로운 댓글이 달렸습니다.", responseNotiDTO.getNotiTitle());
//        assertFalse(responseNotiDTO.isNotiRead());
//    }
//}