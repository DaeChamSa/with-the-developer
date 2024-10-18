package com.developer.postservice.comu.post.command.service;

import com.developer.postservice.client.UserServiceClient;
import com.developer.postservice.common.exception.CustomException;
import com.developer.postservice.common.exception.ErrorCode;
import com.developer.postservice.comu.post.command.dto.ComuPostCreateDTO;
import com.developer.postservice.comu.post.command.dto.ComuPostUpdateDTO;
import com.developer.postservice.comu.post.command.entity.ComuPost;
import com.developer.postservice.comu.post.command.repository.ComuPostRepository;
import com.developer.postservice.dto.ResponseUserDTO;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ComuPostService {

    private final ComuPostRepository comuPostRepository;
    private final UserServiceClient userServiceClient;

    // 커뮤니티 게시글 등록
    @Transactional
    public Long createComuPost(ComuPostCreateDTO comuPostCreateDTO, String userId) {
        ResponseUserDTO user;
        try {
            user = userServiceClient.findByUserID(userId);
        } catch (FeignException e) {
            throw new CustomException(ErrorCode.NOT_FOUND_USER);
        }

        ComuPost post = comuPostCreateDTO.toEntity();
        post.updateUser(user.getUserCode());

        ComuPost savedComuPost = comuPostRepository.save(post);

        return savedComuPost.getComuCode();
    }

    //커뮤니티 게시글 수정
    @Transactional
    public Long updateComuPost(ComuPostUpdateDTO comuPostUpdateDTO, String userId) {
        ResponseUserDTO user;
        // 사용자 정보 조회(로그인 사용자와 동일한지 확인)
        try {
            user = userServiceClient.findByUserID(userId);
        } catch (FeignException e) {
            throw new CustomException(ErrorCode.NOT_FOUND_USER);
        }

        // 게시글 코드로 조회(업데이트 게시글 찾기)
        ComuPost comuPost = comuPostRepository.findById(comuPostUpdateDTO.getComuCode())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_POST));

        // 게시글 작성자 현재 작성자 동일한지 확인
        if (comuPost.getUserCode().equals(user.getUserCode())) {
            comuPost.updateComuPost(comuPostUpdateDTO.getComuSubject(), comuPostUpdateDTO.getComuContent());
        } else {
            throw new CustomException(ErrorCode.NOT_MATCH_ROLE);
        }
        return comuPost.getComuCode();
    }

    //커뮤니티 게시글 삭제
    @Transactional
    public void deleteComuPost(Long comuPostCode, String userId) {
        ResponseUserDTO user;
        // 사용자 정보 조회(로그인 사용자와 동일한지 확인)
        try {
            user = userServiceClient.findByUserID(userId);
        } catch (FeignException e) {
            throw new CustomException(ErrorCode.NOT_FOUND_USER);
        }

        // 게시글 코드로 조회(삭제 게시글 찾기)
        ComuPost comuPost = comuPostRepository.findById(comuPostCode)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_POST));

        // 게시글 작성자 현재 작성자 동일한지 확인
        if (comuPost.getUserCode().equals(user.getUserCode())) {
            comuPostRepository.delete(comuPost);
        } else {
            throw new CustomException(ErrorCode.UNAUTHORIZED_USER);
        }
    }
}
