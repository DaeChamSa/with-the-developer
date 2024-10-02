package com.developer.noti.command.application.service;

import com.developer.common.exception.CustomException;
import com.developer.common.exception.ErrorCode;
import com.developer.noti.command.application.dto.NotiMsgCreateDTO;
import com.developer.noti.command.application.dto.NotiCommentCreateDTO;
import com.developer.noti.command.application.dto.NotiRecruitCreateDTO;
import com.developer.noti.command.domain.aggregate.Noti;
import com.developer.noti.command.domain.aggregate.NotiType;
import com.developer.noti.command.domain.aggregate.PostType;
import com.developer.noti.command.domain.repository.NotiRepository;
import com.developer.user.command.domain.aggregate.User;
import com.developer.user.command.domain.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotiCommandService {

    private final NotiRepository notiRepository;
    private final UserRepository userRepository;

    // 알림 읽음 처리
    @Transactional
    public void readNoti(Long userCode, Long notiCode) {
        Optional<Noti> byNotiCodeAndUserCode = notiRepository.findByNotiCodeAndUserCode(notiCode, userCode);

        if (byNotiCodeAndUserCode.isEmpty()) {
            log.info("알림을 찾을 수 없음 notiCode = {}, userCode = {}", notiCode, userCode);
            throw new CustomException(ErrorCode.NOT_FOUND_NOTI);
        }

        Noti noti = byNotiCodeAndUserCode.get();
        noti.readNoti();
        log.info("읽음 처리 완료 : " + noti.isNotiRead());

        notiRepository.save(noti);
    }

    // 알림 삭제 처리
    @Transactional
    public void deleteNoti(Long userCode, Long notiCode) {
        Optional<Noti> byNotiCodeAndUserCode = notiRepository.findByNotiCodeAndUserCode(notiCode, userCode);

        if (byNotiCodeAndUserCode.isEmpty()) {
            log.info("알림을 찾을 수 없음 notiCode = {}, userCode = {}", notiCode, userCode);
            throw new CustomException(ErrorCode.NOT_FOUND_NOTI);
        }

        Noti noti = byNotiCodeAndUserCode.get();
        log.info("deleteNoti 이전 {}", noti.isNotiDelStatus());
        noti.deleteNoti();
        log.info("deleteNoti 이후 {}", noti.isNotiDelStatus());

        notiRepository.save(noti);
    }

    // 댓글에 대한 알림 발생
    @Transactional
    public Long addCommentEvent(NotiCommentCreateDTO notiCommentCreateDTO) {

        User user = userRepository.findByUserCode(notiCommentCreateDTO.getUserCode())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));

        if (!user.isResNoti()){
            // 알림 수신 여부가 거부되어 있으면 종료
            return null;
        }

        String url = checkPostType(notiCommentCreateDTO.getPostType());

        Noti noti = new Noti(NotiType.NOTI_TYPE_COMMENT.getType(),
                notiCommentCreateDTO.getUserCode(),
                notiCommentCreateDTO.getPostCode(),
                url);

        notiRepository.save(noti);

        return noti.getNotiCode();
    }

    // 쪽지에 대한 알림 발생
    @Transactional
    public void addMsgEvent(NotiMsgCreateDTO notiMsgCreateDTO) {

        User user = userRepository.findByUserCode(notiMsgCreateDTO.getResUserCode())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));

        if (!user.isResNoti()){
            // 알림 수신 여부가 거부되어 있으면 종료
            return;
        }
        Noti noti = new Noti(NotiType.NOTI_TYPE_MESSAGE.getType(),
                "/msg/read-req/" + notiMsgCreateDTO.getMsgCode(),
                notiMsgCreateDTO.getResUserCode()
                );

        notiRepository.save(noti);
    }

    // 채용 공고가 승인 되었을 때의 알림 발생
    @Transactional
    public void addAcceptEvent(NotiRecruitCreateDTO notiRecruitCreateDTO){

        Noti noti = new Noti(NotiType.NOTI_TYPE_ACCEPT.getType(),
                "/recruit/detail/" + notiRecruitCreateDTO.getRecruitCode(),
                notiRecruitCreateDTO.getUserCode()
                );

        notiRepository.save(noti);
    }

    // 채용 공고가 반려 되었을 때의 알림 발생
    @Transactional
    public void addRejectEvent(NotiRecruitCreateDTO notiRecruitCreateDTO){

        Noti noti = new Noti(NotiType.NOTI_TYPE_REJECT.getType(),
                "/recruit/detail/" + notiRecruitCreateDTO.getRecruitCode(),
                notiRecruitCreateDTO.getUserCode()
        );

        notiRepository.save(noti);
    }

    // PostType 확인하기
    private String checkPostType(PostType postType) {

        String postUrl;

        switch (postType){
            case COMMUNITY -> postUrl = "/comu";
            case TEAM -> postUrl = "/team";
            case PROJECT -> postUrl = "/proj";
            case RECRUIT -> postUrl = "/recruit";
            default -> throw new CustomException(ErrorCode.NOT_FOUND_POST_TYPE);
        }

        return postUrl;
    }


}
