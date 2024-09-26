package com.developer.noti.query.service;

import com.developer.noti.query.dto.ResponseNotiDTO;
import com.developer.noti.query.mapper.NotiMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotiQueryService {

    private final NotiMapper notiMapper;

    // 사용자에게 발생된 알림 전체 조회
    public List<ResponseNotiDTO> findAllByUserCode(Long userCode){

        return notiMapper.findAllNotNotiDelStatus(userCode);
    }
}
