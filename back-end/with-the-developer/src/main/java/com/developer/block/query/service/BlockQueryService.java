package com.developer.block.query.service;

import com.developer.block.query.dto.BlockResponseDTO;
import com.developer.block.query.mapper.BlockMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BlockQueryService {

    private final BlockMapper blockMapper;

    @Transactional(readOnly = true)
    public List<BlockResponseDTO> findAll(Long loginUser) {
        return blockMapper.findAll(loginUser);
    }
}
