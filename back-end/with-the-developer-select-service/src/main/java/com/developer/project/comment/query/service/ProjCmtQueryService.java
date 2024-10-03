package com.developer.project.comment.query.service;

import com.developer.common.exception.CustomException;
import com.developer.common.exception.ErrorCode;
import com.developer.project.comment.query.dto.ProjCmtResponseDTO;
import com.developer.project.comment.query.mapper.ProjCmtMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ProjCmtQueryService {

    private final ProjCmtMapper projCmtMapper;

    @Transactional(readOnly = true)
    public List<ProjCmtResponseDTO> readAll(Integer page, Long projPostCode) {
        int offset = (page - 1) * 10;

        Map<String, Object> params = new HashMap<>();
        params.put("offset", offset);
        params.put("projPostCode", projPostCode);

        List<ProjCmtResponseDTO> cmtList = projCmtMapper.findAll(params);

        if (cmtList == null) {
            throw new CustomException(ErrorCode.NOT_FOUND_COMMENT);
        }

        return cmtList;
    }
}
