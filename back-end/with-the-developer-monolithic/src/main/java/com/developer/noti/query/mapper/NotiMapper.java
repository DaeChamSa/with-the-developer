package com.developer.noti.query.mapper;

import com.developer.noti.query.dto.AllNotiDTO;
import com.developer.noti.query.dto.ResponseNotiDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface NotiMapper {

    // 삭제처리 안되어있는 알림들만 
    List<ResponseNotiDTO> findAllNotNotiDelStatus(Long userCode);
    
    // 모든 알림 불러오기
    List<AllNotiDTO> findByAll(Long userCode);

    // 삭제 안 된 읽은 알림 불러오기
    List<ResponseNotiDTO> findByIsRead(Long userCode);

    // 삭제 안 된 안읽은 알림 불러오기
    List<ResponseNotiDTO> findByNotRead(Long userCode);
}
