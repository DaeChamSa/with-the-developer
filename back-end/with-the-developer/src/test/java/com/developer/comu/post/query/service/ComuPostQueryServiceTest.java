package com.developer.comu.post.query.service;

import com.developer.common.exception.CustomException;
import com.developer.common.exception.ErrorCode;
import com.developer.comu.post.command.dto.ComuPostCreateDTO;
import com.developer.comu.post.command.service.ComuPostService;
import com.developer.comu.post.query.dto.ComuPostResponseDTO;
import com.developer.comu.post.query.mapper.ComuPostMapper;
import com.developer.user.command.domain.repository.UserRepository;
import com.developer.user.command.domain.aggregate.User;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Transactional
class ComuPostQueryServiceTest {

    @Autowired
    private ComuPostService comuPostService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ComuPostMapper comuPostMapper;

    @Test
    @DisplayName("커뮤니티 게시글 조회 테스트")
    void selectAllComuPost() {
        //given
        String userId = "user03";

        //사용자 존재 여부 확인
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));

        // 게시글 조회 테스트 위한 등록
        ComuPostCreateDTO comuPostCreateDTO = new ComuPostCreateDTO();
        comuPostCreateDTO.setComuSubject("커뮤니티 게시글 제목");
        comuPostCreateDTO.setComuContent("커뮤니티 게시글 내용");

        Long postId = comuPostService.createComuPost(comuPostCreateDTO, userId);

        //when
        int limit = 10;
        List<ComuPostResponseDTO> allComuPost = comuPostMapper.selectAllComuPost(limit);

        //then
        assertNotNull(allComuPost);
    }

    @Test
    @DisplayName("커뮤니티 게시글 코드 조회 테스트")
    void selectComuPostByCode() {
        //given
        String userId = "user03";

        //사용자 존재 여부 확인
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));

        // 게시글 조회 테스트 위한 등록
        ComuPostCreateDTO comuPostCreateDTO = new ComuPostCreateDTO();
        comuPostCreateDTO.setComuSubject("검색용");
        comuPostCreateDTO.setComuContent("커뮤니티 게시글 내용");

        Long postId = comuPostService.createComuPost(comuPostCreateDTO, userId);

        //when
        comuPostMapper.selectComuPostByCode(postId);

        //then
        assertNotNull(comuPostMapper.selectComuPostByCode(postId));  // 조회 결과가 null이 아님을 확인
    }
}