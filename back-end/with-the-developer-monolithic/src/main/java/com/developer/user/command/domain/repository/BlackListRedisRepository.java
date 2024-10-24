package com.developer.user.command.domain.repository;

import com.developer.user.command.domain.aggregate.BlackListRedis;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface BlackListRedisRepository extends CrudRepository<BlackListRedis, String> {
    Optional<BlackListRedis> findByAccessToken(String accessToken);
    Optional<BlackListRedis> findByUserId(String userId);
}
