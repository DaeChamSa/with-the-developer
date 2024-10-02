package com.developer.comu.comment.query.service;

import com.developer.common.exception.CustomException;
import com.developer.common.exception.ErrorCode;
import com.developer.comu.comment.query.dto.ComuCmtDTO;
import com.developer.comu.comment.query.mapper.ComuCmtMapper;
import com.developer.comu.post.command.repository.ComuPostRepository;
import com.developer.user.command.domain.aggregate.User;
import com.developer.user.command.domain.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Transactional
class ComuCmtQueryServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ComuPostRepository comuPostRepository;

    @Autowired
    private ComuCmtMapper comuCmtMapper;

    @Test
    @DisplayName("커뮤니티 댓글 조회 테스트")
    void findCmt() {
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

        //when
        int limit = 10;
        int page = 10;
        int offset = (page - 1) * page;
        List<ComuCmtDTO> ComuCmtAll = comuCmtMapper.selectComuCmtByPostCode(comuPostCode, limit, offset);

        //then
        assertNotNull(ComuCmtAll);
    }

}