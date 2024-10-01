package com.developer.block.command.infrastructure.repository;

import com.developer.block.command.domain.aggregate.BlockedUser;
import com.developer.block.command.domain.repository.BlockedUserRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaBlockedUserRepository extends JpaRepository<BlockedUser, Long>, BlockedUserRepository {
}
