package com.developer.msgservice.block.command.domain.repository;

import com.developer.msgservice.block.command.domain.aggregate.Block;

public interface BlockRepository {

    Block save(Block block);

    void deleteById(Long userCode);
}
