package com.developer.user.command.domain.repository;

import com.developer.user.command.domain.aggregate.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUserId(String userId);

    Optional<User> findByUserEmail(String userEmail);

    Optional<User> findByUserNick(String userNick);

    Optional<User> findByUserPhone(String userPhone);

    Optional<User> findByUserCode(Long userCode);

    // 아이디가 이미 있는지 확인
    boolean existsByUserId(String userId);
}
