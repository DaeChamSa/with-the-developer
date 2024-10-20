package com.developer.msgservice.block.command.infrastructure.repository;

import com.developer.msgservice.block.command.domain.aggregate.Block;
import com.developer.msgservice.block.command.domain.repository.BlockRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaBlockRepository extends JpaRepository<Block, Long>, BlockRepository {
}
