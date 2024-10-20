package com.developer.user.command.infrastructure.repository;

import com.developer.user.command.domain.aggregate.BlackList;
import com.developer.user.command.domain.repository.BlackListRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaBlackListRepository extends BlackListRepository, JpaRepository<BlackList, Long> {
}
