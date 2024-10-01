package com.developer.noti.command.application.service;

import com.developer.common.exception.CustomException;
import com.developer.common.exception.ErrorCode;
import com.developer.noti.command.application.dto.NotiMsgCreateDTO;
import com.developer.noti.command.application.dto.NotiPostCreateDTO;
import com.developer.noti.command.application.dto.NotiRecruitCreateDTO;
import com.developer.noti.command.domain.aggregate.Noti;
import com.developer.noti.command.domain.aggregate.NotiType;
import com.developer.noti.command.domain.aggregate.PostType;
import com.developer.noti.command.domain.repository.NotiRepository;
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
    public void addCommentEvent(NotiPostCreateDTO notiPostCreateDTO) {

        String url = checkPostType(notiPostCreateDTO.getPostType());

        Noti noti = new Noti(NotiType.NOTI_TYPE_COMMENT.getType(),
                notiPostCreateDTO.getUserCode(),
                notiPostCreateDTO.getPostCode(),
                url);

        notiRepository.save(noti);
    }

    // 쪽지에 대한 알림 발생
    @Transactional
    public void addMsgEvent(NotiMsgCreateDTO notiMsgCreateDTO) {

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
