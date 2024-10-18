package com.developer.postservice.project.comment.command.application.service;

import com.developer.postservice.client.NotiServiceClient;
import com.developer.postservice.client.UserServiceClient;
import com.developer.postservice.common.exception.CustomException;
import com.developer.postservice.common.exception.ErrorCode;
import com.developer.postservice.dto.NotiCommentCreateDTO;
import com.developer.postservice.project.comment.command.application.dto.ProjCmtRequestDTO;
import com.developer.postservice.project.comment.command.domain.aggregate.ProjCmt;
import com.developer.postservice.project.comment.command.domain.repository.ProjCmtRepository;
import com.developer.postservice.project.post.command.domain.aggregate.ProjPost;
import com.developer.postservice.project.post.command.domain.repository.ProjPostRepository;
import com.developer.postservice.project.comment.command.application.dto.ProjCmtRequestDTO;
import com.developer.postservice.project.comment.command.domain.aggregate.ProjCmt;
import com.developer.postservice.project.comment.command.domain.repository.ProjCmtRepository;
import com.developer.postservice.project.post.command.domain.aggregate.ProjPost;
import com.developer.postservice.project.post.command.domain.repository.ProjPostRepository;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ProjCmtCommandService {

    private final ProjCmtRepository projCmtRepository;
    private final ProjPostRepository projPostRepository;
    private final UserServiceClient userServiceClient;
    private final NotiServiceClient notiServiceClient;
    @Transactional
    public Long createCmt(Long projPostCode, Long userCode, ProjCmtRequestDTO projCmtRequestDTO) {

        ProjPost projPost = projPostRepository.findById(projPostCode)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_POST));

        try {
            userServiceClient.findByUserCode(userCode);
        } catch (FeignException e) {
            throw new CustomException(ErrorCode.NOT_FOUND_USER);
        }

        ProjCmt projCmt = projCmtRequestDTO.toEntity();
        projCmt.updateUserAndPost(projPostCode, userCode);
        ProjCmt savedCmt = projCmtRepository.save(projCmt);

        // 알림 생성 (게시글 작성 유저코드, 게시글 코드, 게시글 타입)
        NotiCommentCreateDTO notiCommentCreateDTO =
                new NotiCommentCreateDTO(
                        projPost.getUserCode()
                        , projPost.getProjPostCode()
                        , "PROJECT");

        notiServiceClient.addCommentEvent(notiCommentCreateDTO);

        return savedCmt.getProjCmtCode();
    }

    @Transactional
    public void updateCmt(Long projPostCode, Long userCode, Long projCmtCode, ProjCmtRequestDTO projCmtRequestDTO) {
        projPostRepository.findById(projPostCode)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_POST));

        ProjCmt projCmt = projCmtRepository.findById(projCmtCode)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_COMMENT));

        if (!projCmt.getUserCode().equals(userCode)) {
            throw new CustomException(ErrorCode.UNAUTHORIZED_USER_COMMENT);
        }

        projCmt.updateCmt(projCmtRequestDTO.getProjCmtContent());
    }

    @Transactional
    public void deleteCmt(Long projPostCode, Long userCode, Long projCmtCode) {
        projPostRepository.findById(projPostCode)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_POST));

        ProjCmt projCmt = projCmtRepository.findById(projCmtCode)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_COMMENT));

        if (!projCmt.getUserCode().equals(userCode)) {
            throw new CustomException(ErrorCode.UNAUTHORIZED_USER_COMMENT);
        }

        projCmtRepository.deleteById(projCmtCode);
    }
}
