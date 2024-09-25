package com.developer.comu.comment.command.service;

import com.developer.common.exception.CustomException;
import com.developer.common.exception.ErrorCode;
import com.developer.comu.comment.command.dto.ComuCmtCreateDTO;
import com.developer.comu.comment.command.dto.ComuCmtUpdateDTO;
import com.developer.comu.comment.command.entity.ComuCmt;
import com.developer.comu.comment.command.repository.ComuCmtRepository;
import com.developer.comu.post.command.repository.ComuPostRepository;
import com.developer.user.command.entity.User;
import com.developer.user.command.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ComuCmtService {

    private final ComuCmtRepository comuCmtRepository;
    private final UserRepository userRepository;
    private final ComuPostRepository comuPostRepository;


    // 커뮤니티 게시글 댓글 등록
    @Transactional
    public Long createComuCmt(Long comuPostCode, Long userCode, ComuCmtCreateDTO comuCmtCreateDTO) {
        User user = userRepository.findById(userCode)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));

        // 게시글이 존재하는지 확인
        comuPostRepository.findById(comuPostCode)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_POST));


        // 새로운 댓글 엔티티 생성 (생성자를 사용)
        ComuCmt comuCmt = new ComuCmt(comuPostCode, user, comuCmtCreateDTO.getComuCmtContent());
        ComuCmt savedComuCmt = comuCmtRepository.save(comuCmt);

        return savedComuCmt.getComuCmtCode();
    }

    // 커뮤니티 댓글 수정
    @Transactional
    public Long updateComuCnt(Long comuPostCode, Long userCode, ComuCmtUpdateDTO comuCmtUpdateDTO) {

        User user = userRepository.findById(userCode).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));

        ComuCmt comuCmt = comuCmtRepository.findById(comuCmtUpdateDTO.getComuCmtCode())
                .orElseThrow(() -> new CustomException(ErrorCode.UNAUTHORIZED_USER_COMMENT));

        if (comuCmt.getUser().equals(user)) {
            comuCmt.updateComuCmt(comuCmtUpdateDTO.getComuContent());
        } else {
            throw new CustomException(ErrorCode.NOT_MATCH_ROLE);
        }
        return comuCmt.getComuCmtCode();
    }

    // 커뮤니티 댓글 삭제
    @Transactional
    public Long deleteComuCmt(Long comuPostCode, Long currentUserCode) {
        User user = userRepository.findById(currentUserCode).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));

        ComuCmt comuCmt = comuCmtRepository.findById(comuPostCode)
                .orElseThrow(() -> new CustomException(ErrorCode.UNAUTHORIZED_USER_COMMENT));

        if (comuCmt.getUser().equals(user)) {
            comuCmtRepository.delete(comuCmt);
        } else {
            throw new CustomException(ErrorCode.NOT_MATCH_ROLE);
        }

        return comuCmt.getComuCmtCode();
    }
}
