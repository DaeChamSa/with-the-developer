package com.developer.project.comment.command.application.service;

import com.developer.common.exception.CustomException;
import com.developer.common.exception.ErrorCode;
import com.developer.project.comment.command.application.dto.ProjCmtRequestDTO;
import com.developer.project.comment.command.domain.aggregate.ProjCmt;
import com.developer.project.comment.command.domain.repository.ProjCmtRepository;
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

    @Transactional
    public Long createCmt(Long projPostCode, Long userCode, ProjCmtRequestDTO projCmtRequestDTO) {
        projPostRepository.findById(projPostCode)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_POST));
        User user = userRepository.findById(userCode)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));

        ProjCmt projCmt = projCmtRequestDTO.toEntity();
        projCmt.updateUserAndPost(projPostCode, userCode);
        ProjCmt savedCmt = projCmtRepository.save(projCmt);

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
