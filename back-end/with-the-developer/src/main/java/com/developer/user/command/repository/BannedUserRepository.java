package com.developer.user.command.repository;

import com.developer.user.command.entity.BannedUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BannedUserRepository extends JpaRepository<BannedUser , Long> {
}
