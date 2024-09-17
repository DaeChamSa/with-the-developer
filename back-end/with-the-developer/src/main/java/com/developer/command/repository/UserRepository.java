package com.developer.command.repository;

import com.developer.command.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUserId(String userId);

    Optional<User> findByUserEmail(String userEmail);

    Optional<User> findByUserNick(String userNick);

    Optional<User> findByUserPhone(String userPhone);
}
