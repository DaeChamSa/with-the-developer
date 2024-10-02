package com.developer.comu.comment.command.service;

import com.developer.common.exception.CustomException;
import com.developer.common.exception.ErrorCode;
import com.developer.comu.comment.command.dto.ComuCmtCreateDTO;
import com.developer.comu.comment.command.dto.ComuCmtUpdateDTO;
import com.developer.comu.comment.command.entity.ComuCmt;
import com.developer.comu.comment.command.repository.ComuCmtRepository;
import com.developer.comu.post.command.repository.ComuPostRepository;
import com.developer.user.command.domain.aggregate.User;
import com.developer.user.command.domain.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ComuCmtServiceTest {

    @Autowired
    private ComuCmtService comuCmtService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ComuPostRepository comuPostRepository;

    @Autowired
    private ComuCmtRepository comuCmtRepository;

    @Test
    @DisplayName("커뮤니티 게시글 댓글 등록 테스트")
    void createComuCmtTest() {
        // given
        // 댓글 등록 위한 로그인, 게시글 존재 확인
        // 로그인한 사용자 아이디
        String userId = "user03"; // 유효한 사용자 ID로 교체필수
        Long comuPostCode = 4L; // 유효한 커뮤니티 게시글 코드로 교체필수

        // 사용자 존재 여부 확인
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));

        // 게시글 존재 확인
        comuPostRepository.findById(comuPostCode)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_POST));

        ComuCmtCreateDTO comuCmtCreateDTO = new ComuCmtCreateDTO();
        comuCmtCreateDTO.setComuCmtContent("커뮤니티 댓글 등록");

        //when
        Long createdCmtId = comuCmtService.createComuCmt(comuPostCode, user.getUserCode(), comuCmtCreateDTO);

        //then
        assertNotNull(createdCmtId);

    }

    @Test
    @DisplayName("커뮤니티 게시글 댓글 수정 테스트")
    void updateComuCmtTest() {
        // given
        // 댓글 수정 위한 로그인, 게시글 확인, 댓글 등록
        // 로그인한 사용자 아이디
        String userId = "user03"; // 유효한 사용자 ID로 교체필수
        Long comuPostCode = 4L; // 유효한 커뮤니티 게시글 코드로 교체필수

        // 사용자 존재 여부 확인
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));

        // 게시글 존재 확인
        comuPostRepository.findById(comuPostCode)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_POST));

        // 댓글 등록
        ComuCmtCreateDTO comuCmtCreateDTO = new ComuCmtCreateDTO();
        comuCmtCreateDTO.setComuCmtContent("커뮤니티 댓글 등록");

        Long createdCmtId = comuCmtService.createComuCmt(comuPostCode, user.getUserCode(), comuCmtCreateDTO);

        //댓글 수정
        ComuCmtUpdateDTO comuCmtUpdateDTO = new ComuCmtUpdateDTO();
        comuCmtUpdateDTO.setComuCmtCode(createdCmtId);
        comuCmtUpdateDTO.setComuContent("커뮤니티 댓글 수정");

        //when
        Long updateCmtId = comuCmtService.updateComuCnt(comuPostCode, user.getUserCode(), comuCmtUpdateDTO);

        ComuCmt updateCmt = comuCmtRepository.findById(updateCmtId)
                .orElseThrow(()->new CustomException(ErrorCode.NOT_FOUND_COMMENT));

        //then
        assertNotNull(updateCmtId);
        assertEquals("커뮤니티 댓글 수정",updateCmt.getComuCmtContent());
    }

    @Test
    @DisplayName("커뮤니티 게시글 댓글 삭제 테스트")
    void deleteComuCmtTest() {
        // given
        // 댓글 수정 위한 로그인, 게시글 확인, 댓글 등록
        // 로그인한 사용자 아이디
        String userId = "user03"; // 유효한 사용자 ID로 교체필수
        Long comuPostCode = 4L; // 유효한 커뮤니티 게시글 코드로 교체필수

        // 사용자 존재 여부 확인
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));

        // 게시글 존재 확인
        comuPostRepository.findById(comuPostCode)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_POST));

        // 댓글 등록
        ComuCmtCreateDTO comuCmtCreateDTO = new ComuCmtCreateDTO();
        comuCmtCreateDTO.setComuCmtContent("커뮤니티 댓글 등록");

        Long createdCmtId = comuCmtService.createComuCmt(comuPostCode, user.getUserCode(), comuCmtCreateDTO);

        //when
        comuCmtService.deleteComuCmt(createdCmtId, user.getUserCode());

        //then
        assertTrue(comuCmtRepository.findById(createdCmtId).isEmpty());
    }
}