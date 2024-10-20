package com.developer.postservice.comu.comment.command.service;

import com.developer.postservice.client.NotiServiceClient;
import com.developer.postservice.client.UserServiceClient;
import com.developer.postservice.common.exception.CustomException;
import com.developer.postservice.common.exception.ErrorCode;
import com.developer.postservice.comu.comment.command.dto.ComuCmtCreateDTO;
import com.developer.postservice.comu.comment.command.dto.ComuCmtUpdateDTO;
import com.developer.postservice.comu.comment.command.entity.ComuCmt;
import com.developer.postservice.comu.comment.command.repository.ComuCmtRepository;
import com.developer.postservice.comu.post.command.entity.ComuPost;
import com.developer.postservice.comu.post.command.repository.ComuPostRepository;
import com.developer.postservice.dto.NotiCommentCreateDTO;
import com.developer.postservice.dto.ResponseUserDTO;
import com.developer.postservice.client.NotiServiceClient;
import com.developer.postservice.client.UserServiceClient;
import com.developer.postservice.common.exception.CustomException;
import com.developer.postservice.common.exception.ErrorCode;
import com.developer.postservice.dto.NotiCommentCreateDTO;
import com.developer.postservice.dto.ResponseUserDTO;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ComuCmtService {

    private final ComuCmtRepository comuCmtRepository;
    private final ComuPostRepository comuPostRepository;
    private final UserServiceClient userServiceClient;
    private final NotiServiceClient notiServiceClient;

    // 커뮤니티 게시글 댓글 등록
    @Transactional
    public Long createComuCmt(Long comuPostCode, Long userCode, ComuCmtCreateDTO comuCmtCreateDTO) {
        try {
            userServiceClient.findByUserCode(userCode);
        } catch (FeignException e) {
            throw new CustomException(ErrorCode.NOT_FOUND_USER);
        }

        // 게시글이 존재하는지 확인
        ComuPost comuPost = comuPostRepository.findById(comuPostCode)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_POST));


        // 새로운 댓글 엔티티 생성 (생성자를 사용)
        ComuCmt comuCmt = new ComuCmt(comuPostCode, userCode, comuCmtCreateDTO.getComuCmtContent());
        ComuCmt savedComuCmt = comuCmtRepository.save(comuCmt);

        // 알림 생성 (게시글 작성 유저코드, 게시글 코드, 게시글 타입)
        NotiCommentCreateDTO notiCommentCreateDTO =
                new NotiCommentCreateDTO(
                        comuPost.getUserCode()
                        , comuPostCode
                        , "COMMUNITY");

        notiServiceClient.addCommentEvent(notiCommentCreateDTO);

        return savedComuCmt.getComuCmtCode();
    }

    // 커뮤니티 댓글 수정
    @Transactional
    public Long updateComuCnt(Long userCode, ComuCmtUpdateDTO comuCmtUpdateDTO) {
        ResponseUserDTO user;
        try {
            user = userServiceClient.findByUserCode(userCode);
        } catch (FeignException e) {
            throw new CustomException(ErrorCode.NOT_FOUND_USER);
        }

        ComuCmt comuCmt = comuCmtRepository.findById(comuCmtUpdateDTO.getComuCmtCode())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_COMMENT));

        comuPostRepository.findById(comuCmt.getComuPostCode())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_POST));

        if (comuCmt.getUserCode().equals(user.getUserCode())) {
            comuCmt.updateComuCmt(comuCmtUpdateDTO.getComuContent());
        } else {
            throw new CustomException(ErrorCode.UNAUTHORIZED_USER_COMMENT);
        }
        return comuCmt.getComuCmtCode();
    }

    // 커뮤니티 댓글 삭제
    @Transactional
    public Long deleteComuCmt(Long comuCmtCode, Long currentUserCode) {
        ResponseUserDTO user;
        try {
            user = userServiceClient.findByUserCode(currentUserCode);
        } catch (FeignException e) {
            throw new CustomException(ErrorCode.NOT_FOUND_USER);
        }
        ComuCmt comuCmt = comuCmtRepository.findById(comuCmtCode)
                .orElseThrow(() -> new CustomException(ErrorCode.UNAUTHORIZED_USER_COMMENT));

        comuPostRepository.findById(comuCmt.getComuPostCode())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_POST));

        if (comuCmt.getUserCode().equals(user.getUserCode())) {
            comuCmtRepository.delete(comuCmt);
        } else {
            throw new CustomException(ErrorCode.NOT_MATCH_ROLE);
        }

        return comuCmt.getComuCmtCode();
    }
}
