package com.developer.comu.post.command.service;

import com.developer.common.exception.CustomException;
import com.developer.common.exception.ErrorCode;
import com.developer.comu.post.command.dto.ComuPostCreateDTO;
import com.developer.comu.post.command.dto.ComuPostUpdateDTO;
import com.developer.comu.post.command.entity.ComuPost;
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
class ComuPostServiceTest {

    @Autowired
    private ComuPostService comuPostService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ComuPostRepository comuPostRepository;

    @Test
    @DisplayName("커뮤니티 게시글 등록 테스트")
    void registComuPost() {
        // given
        // 로그인한 사용자 ID
        String userId = "user03"; // 유효한 사용자 ID로 교체하세요

        // 사용자 존재 여부 확인
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));

        ComuPostCreateDTO comuPostCreateDTO = new ComuPostCreateDTO();
        comuPostCreateDTO.setComuSubject("커뮤니티 게시글 제목");
        comuPostCreateDTO.setComuContent("커뮤니티 게시글 내용");

        // when
        Long createdPostId = comuPostService.createComuPost(comuPostCreateDTO, userId);

        // then
        assertNotNull(createdPostId);
    }

    @Test
    @DisplayName("커뮤니티 게시글 수정 테스트")
    void updateComuPost() {
        //given
        String userId = "user03";

        //사용자 존재 여부 확인
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));

        // 게시글 수정 테스트 위한 등록
        ComuPostCreateDTO comuPostCreateDTO = new ComuPostCreateDTO();
        comuPostCreateDTO.setComuSubject("커뮤니티 게시글 제목");
        comuPostCreateDTO.setComuContent("커뮤니티 게시글 내용");

        Long postId = comuPostService.createComuPost(comuPostCreateDTO, userId);

        ComuPostUpdateDTO comuPostUpdateDTO = new ComuPostUpdateDTO();
        comuPostUpdateDTO.setComuCode(postId);
        comuPostUpdateDTO.setComuSubject("커뮤니티 게시글 제목 수정");
        comuPostUpdateDTO.setComuContent("커뮤니티 게시글 내용 수정");

        //when
        Long updatePostId = comuPostService.updateComuPost(comuPostUpdateDTO, userId);

        ComuPost updatePost = comuPostRepository.findById(updatePostId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_POST));

        //then
        assertNotNull(updatePostId);
        assertEquals("커뮤니티 게시글 제목 수정", updatePost.getComuSubject());
    }

    @Test
    @DisplayName("커뮤니티 게시글 삭제 테스트")
    void deleteComuPost() {
        //given
        String userId = "user03";

        //사용자 존재 여부 확인
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));

        // 게시글 삭제 테스트 위한 등록
        ComuPostCreateDTO comuPostCreateDTO = new ComuPostCreateDTO();
        comuPostCreateDTO.setComuSubject("커뮤니티 게시글 제목");
        comuPostCreateDTO.setComuContent("커뮤니티 게시글 내용");

        Long postId = comuPostService.createComuPost(comuPostCreateDTO, userId);

        //when
        comuPostService.deleteComuPost(postId, userId);

        //then
        assertTrue(comuPostRepository.findById(postId).isEmpty());
    }
}