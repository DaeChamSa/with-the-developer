package com.developer.user.command.domain.repository;

import com.developer.user.command.domain.aggregate.RefreshTokenRedis;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RefreshTokenRedisRepository extends CrudRepository<RefreshTokenRedis, String> {
    // userId 로 찾기
    Optional<RefreshTokenRedis> findByUserId(String userId);
    // AccessToken 으로 찾기
    Optional<RefreshTokenRedis> findByAccessToken(String accessToken);
    // RefreshToken 으로 찾기
    Optional<RefreshTokenRedis> findByRefreshToken(String refreshToken);
}