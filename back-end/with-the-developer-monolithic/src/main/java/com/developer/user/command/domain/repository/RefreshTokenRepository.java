package com.developer.user.command.domain.repository;

import com.developer.user.command.domain.aggregate.RefreshToken;

import java.util.Optional;

public interface RefreshTokenRepository {

    Optional<RefreshToken> findByToken(String token);

    Optional<RefreshToken> findByUserId(String userId);

    RefreshToken save(RefreshToken refreshToken);
}
