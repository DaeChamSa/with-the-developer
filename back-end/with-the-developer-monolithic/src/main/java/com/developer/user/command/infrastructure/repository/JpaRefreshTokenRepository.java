package com.developer.user.command.infrastructure.repository;

import com.developer.user.command.domain.aggregate.RefreshToken;
import com.developer.user.command.domain.repository.RefreshTokenRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaRefreshTokenRepository  extends RefreshTokenRepository, JpaRepository<RefreshToken, String> {
}
