package com.developer.comu.comment.query.service;

import com.developer.comu.comment.query.dto.ComuCmtDTO;
import com.developer.comu.comment.query.mapper.ComuCmtMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ComuCmtQueryService {

    private final ComuCmtMapper comuCmtMapper;

    private static final int PAGE_SIZE = 10; // 한 페이지에 표시할 댓글 수

    // 커뮤니티 게시글 댓글 조회
    @Transactional(readOnly = true)
    public List<ComuCmtDTO> getComuCmtListByPostCode(Long comuPostCode, int page) {
        int offset = (page - 1) * PAGE_SIZE;
        int limit = PAGE_SIZE;

        // Mapper 인터페이스를 이용해 SQL 실행
        List<ComuCmtDTO> comuCmtDTOList = comuCmtMapper.selectComuCmtByPostCode(comuPostCode, offset, limit);

        return comuCmtDTOList;
    }

}
