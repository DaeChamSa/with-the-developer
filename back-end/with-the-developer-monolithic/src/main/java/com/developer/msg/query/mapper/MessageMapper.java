package com.developer.msg.query.mapper;

import com.developer.msg.query.dto.ReqMsgResponseDTO;
import com.developer.msg.query.dto.ResMsgResponseDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface MessageMapper {

    List<ReqMsgResponseDTO> findAllReqMsg(Long userCode);

    List<ResMsgResponseDTO> findAllResMsg(Long userCode);

    ReqMsgResponseDTO findReqMsgByMsgCodeAndUserCode(Map<String, Object> params);

    ResMsgResponseDTO findResMsgByMsgCodeAndUserCode(Map<String, Object> params);

    List<ResMsgResponseDTO> findAllUnReadResMsg(Long userCode);
}
