package com.developer.project.comment.command.application.service;

import com.developer.common.exception.CustomException;
import com.developer.common.exception.ErrorCode;
import com.developer.noti.command.application.dto.NotiCommentCreateDTO;
import com.developer.noti.command.application.service.NotiCommandService;
import com.developer.noti.command.domain.aggregate.PostType;
import com.developer.project.comment.command.application.dto.ProjCmtRequestDTO;
import com.developer.project.comment.command.domain.aggregate.ProjCmt;
import com.developer.project.comment.command.domain.repository.ProjCmtRepository;
import com.developer.project.post.command.domain.aggregate.ProjPost;
import com.developer.project.post.command.domain.repository.ProjPostRepository;
import com.developer.user.command.domain.aggregate.User;
import com.developer.user.command.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ProjCmtCommandService {

    private final ProjCmtRepository projCmtRepository;
    private final UserRepository userRepository;
    private final ProjPostRepository projPostRepository;
    private final NotiCommandService notiCommandService;

    @Transactional
    public Long createCmt(Long projPostCode, Long userCode, ProjCmtRequestDTO projCmtRequestDTO) {

        ProjPost projPost = projPostRepository.findById(projPostCode)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_POST));
        User user = userRepository.findById(userCode)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));

        ProjCmt projCmt = projCmtRequestDTO.toEntity();
        projCmt.updateUserAndPost(projPostCode, userCode);
        ProjCmt savedCmt = projCmtRepository.save(projCmt);

        // 알림 생성 (게시글 작성 유저코드, 게시글 코드, 게시글 타입)
        NotiCommentCreateDTO notiCommentCreateDTO =
                new NotiCommentCreateDTO(
                        projPost.getUserCode()
                        , projPost.getProjPostCode()
                        , PostType.PROJECT);

        notiCommandService.addCommentEvent(notiCommentCreateDTO);

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
