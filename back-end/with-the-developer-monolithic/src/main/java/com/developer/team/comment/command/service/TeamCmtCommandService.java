package com.developer.team.comment.command.service;

import com.developer.common.exception.CustomException;
import com.developer.common.exception.ErrorCode;
import com.developer.noti.command.application.dto.NotiCommentCreateDTO;
import com.developer.noti.command.application.service.NotiCommandService;
import com.developer.noti.command.domain.aggregate.PostType;
import com.developer.team.comment.command.dto.TeamCmtRegistDTO;
import com.developer.team.comment.command.dto.TeamCmtUpdateDTO;
import com.developer.team.comment.command.entity.TeamCmt;
import com.developer.team.comment.command.repository.TeamCmtRepository;
import com.developer.team.post.command.entity.TeamPost;
import com.developer.team.post.command.repository.TeamPostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class TeamCmtCommandService {

    private final TeamCmtRepository teamCmtRepository;
    private final TeamPostRepository teamPostRepository;
    private final NotiCommandService notiCommandService;

    @Transactional
    public Long registTeamCmt(TeamCmtRegistDTO teamCmtRegistDTO) {

        // 해당 게시글 존재 유무 확인
        TeamPost teamPost = teamPostRepository
                .findById(teamCmtRegistDTO.getTeamPostCode())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_POST));

        // 게시글 삭제 유무 확인
        if(teamPost.isDelStatus()){
            throw new CustomException(ErrorCode.DELETED_POST);
        }

        TeamCmt newTeamCmt = TeamCmt.builder()
                .teamCmt(teamCmtRegistDTO.getTeamCmt())
                .teamPostCode(teamCmtRegistDTO.getTeamPostCode())
                .userCode(teamCmtRegistDTO.getUserCode())
                .build();

        teamCmtRepository.save(newTeamCmt);

        // 알림 생성 (게시글 작성 유저코드, 게시글 코드, 게시글 타입)
        NotiCommentCreateDTO notiCommentCreateDTO =
                new NotiCommentCreateDTO(
                        teamPost.getUser().getUserCode()
                        , teamPost.getTeamPostCode()
                        , PostType.TEAM);

        notiCommandService.addCommentEvent(notiCommentCreateDTO);

        return newTeamCmt.getTeamCmtCode();
    }

    @Transactional
    public void updateTeamCmt(TeamCmtUpdateDTO teamCmtUpdateDTO, Long cmtCode) {

        // 수정할 댓글 찾기
        TeamCmt updateCmt = teamCmtRepository.findById(cmtCode)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_COMMENT));

        // 삭제 된 댓글인지 확인
        if(updateCmt.isDelStatus()){
            throw new CustomException(ErrorCode.DELETED_COMMENT);
        }

        // 현재 로그인 중인 유저의 댓글인지 확인
        if(!updateCmt.getUserCode().equals(teamCmtUpdateDTO.getUserCode())) {
            throw new CustomException(ErrorCode.UNAUTHORIZED_USER_COMMENT);
        }

        // 댓글 업데이트
        updateCmt.updateTeamCmt(teamCmtUpdateDTO.getTeamCmt());

        teamCmtRepository.save(updateCmt);
    }

    public void deleteTeamCmt(Long cmtCode, Long loginUser) {

        TeamCmt deleteCmt = teamCmtRepository.findById(cmtCode)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_COMMENT));

        // 삭제 된 댓글인지 확인
        if(deleteCmt.isDelStatus()){
            throw new CustomException(ErrorCode.DELETED_COMMENT);
        }

        // 현재 로그인 중인 유저의 댓글인지 확인
        if(!deleteCmt.getUserCode().equals(loginUser)) {
            throw new CustomException(ErrorCode.UNAUTHORIZED_USER_COMMENT);
        }

        teamCmtRepository.delete(deleteCmt);
    }
}
