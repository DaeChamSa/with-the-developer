package com.developer.msgservice.msg.command.application.service;


import com.developer.msgservice.block.command.domain.repository.BlockedUserRepository;
import com.developer.msgservice.client.NotiServiceClient;
import com.developer.msgservice.client.UserServiceClient;
import com.developer.msgservice.common.exception.CustomException;
import com.developer.msgservice.common.exception.ErrorCode;
import com.developer.msgservice.dto.NotiMsgCreateDTO;
import com.developer.msgservice.msg.command.application.dto.MessageRequestDTO;
import com.developer.msgservice.msg.command.domain.aggregate.ReqMsg;
import com.developer.msgservice.msg.command.domain.aggregate.ResMsg;
import com.developer.msgservice.msg.command.domain.repository.ReqMsgRepository;
import com.developer.msgservice.msg.command.domain.repository.ResMsgRepository;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MessageCommandService {

    private final ReqMsgRepository reqMsgRepository;
    private final ResMsgRepository resMsgRepository;
    private final BlockedUserRepository blockedUserRepository;
    private final UserServiceClient userServiceClient;
    private final NotiServiceClient notiServiceClient;

    @Transactional
    public Long sendMessage(MessageRequestDTO messageRequestDTO, Long userCode) {
        try {
            userServiceClient.findByUserCode(userCode);
        } catch(FeignException e) {
            throw new CustomException(ErrorCode.NOT_FOUND_USER);
        }

        try {
            userServiceClient.findByUserCode(messageRequestDTO.getRecipientUserCode());
        } catch(FeignException e) {
            throw new CustomException(ErrorCode.NOT_FOUND_USER);
        }

        if (userCode.equals(messageRequestDTO.getRecipientUserCode())) {
            throw new CustomException(ErrorCode.NO_VALID_MESSAGE_USER);
        }

        if (blockedUserRepository.existsByBlockUserCodeAndUserCode(messageRequestDTO.getRecipientUserCode(), userCode)) {
            throw new CustomException(ErrorCode.BLOCKED_BY_USER);
        }

        ReqMsg reqMsg = messageRequestDTO.toEntity();
        reqMsg.updateSender(userCode);
        reqMsgRepository.save(reqMsg);

        ResMsg resMsg = ResMsg.builder()
                .msgCode(reqMsg.getMsgCode())
                .userCode(messageRequestDTO.getRecipientUserCode())
                .build();
        resMsgRepository.save(resMsg);

        // 알림 생성 (메세지 코드, 보낸사람 유저코드, 받은사람 유저코드)
        NotiMsgCreateDTO notiMsgCreateDTO=
                new NotiMsgCreateDTO(
                        resMsg.getMsgCode()
                        , reqMsg.getUserCode()
                        , resMsg.getUserCode()
                );

        notiServiceClient.addMsgEvent(notiMsgCreateDTO);

        return reqMsg.getMsgCode();
    }

    @Transactional
    public void updateReadStatus(Long msgCode, Long recipientUserCode) {
        ResMsg resMsg = resMsgRepository.findById(msgCode)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_MESSAGE));

        if (!resMsg.getUserCode().equals(recipientUserCode)) {
            throw new CustomException(ErrorCode.UNAUTHORIZED_USER_MESSAGE);
        }

        resMsg.updateRead(true);
    }

    @Transactional
    public void deleteSentMessage(Long msgCode, Long senderUserCode) {
        ReqMsg reqMsg = reqMsgRepository.findById(msgCode)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_MESSAGE));

        if (!reqMsg.getUserCode().equals(senderUserCode)) {
            throw new CustomException(ErrorCode.UNAUTHORIZED_USER_MESSAGE);
        } else {
            reqMsgRepository.deleteById(msgCode);
        }
    }

    @Transactional
    public void deleteReceivedMessage(Long msgCode, Long recipientUserCode) {
        ResMsg resMsg = resMsgRepository.findById(msgCode)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_MESSAGE));

        if (!resMsg.getUserCode().equals(recipientUserCode)) {
            throw new CustomException(ErrorCode.UNAUTHORIZED_USER_MESSAGE);
        } else {
            resMsgRepository.deleteById(msgCode);
        }
    }
}
