package com.developer.user.command.repository;

import com.developer.user.command.entity.BannedUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface BannedUserRepository extends JpaRepository<BannedUser , Long> {
    List<BannedUser> findByBannedDateBefore(LocalDateTime tenDaysAgo);
}
