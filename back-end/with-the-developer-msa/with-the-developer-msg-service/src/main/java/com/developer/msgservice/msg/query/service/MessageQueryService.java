package com.developer.msgservice.msg.query.service;

import com.developer.msgservice.common.exception.CustomException;
import com.developer.msgservice.common.exception.ErrorCode;
import com.developer.msgservice.msg.query.dto.ReqMsgResponseDTO;
import com.developer.msgservice.msg.query.dto.ResMsgResponseDTO;
import com.developer.msgservice.msg.query.mapper.MessageMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MessageQueryService {

    private final MessageMapper messageMapper;

    @Transactional(readOnly = true)
    public List<ReqMsgResponseDTO> findAllReqMsg(Long userCode) {
        return messageMapper.findAllReqMsg(userCode);
    }

    @Transactional(readOnly = true)
    public List<ResMsgResponseDTO> findAllResMsg(Long userCode) {
        return messageMapper.findAllResMsg(userCode);
    }

    @Transactional(readOnly = true)
    public ReqMsgResponseDTO findReqMsgByMsgCodeAndUserCode(Long msgCode, Long userCode) {
        Map<String, Object> params = new HashMap<>();
        params.put("msgCode", msgCode);
        params.put("userCode", userCode);

        ReqMsgResponseDTO reqMsg = messageMapper.findReqMsgByMsgCodeAndUserCode(params);

        if (reqMsg == null) {
            throw new CustomException(ErrorCode.NOT_FOUND_MESSAGE);
        }

        return reqMsg;
    }

    @Transactional(readOnly = true)
    public ResMsgResponseDTO findResMsgByMsgCodeAndUserCode(Long msgCode, Long userCode) {
        Map<String, Object> params = new HashMap<>();
        params.put("msgCode", msgCode);
        params.put("userCode", userCode);

        ResMsgResponseDTO resMsg = messageMapper.findResMsgByMsgCodeAndUserCode(params);

        if (resMsg == null) {
            throw new CustomException(ErrorCode.NOT_FOUND_MESSAGE);
        }

        return resMsg;
    }

    @Transactional(readOnly = true)
    public List<ResMsgResponseDTO> findAllUnReadResMsg(Long userCode) {
        return messageMapper.findAllUnReadResMsg(userCode);
    }
}
