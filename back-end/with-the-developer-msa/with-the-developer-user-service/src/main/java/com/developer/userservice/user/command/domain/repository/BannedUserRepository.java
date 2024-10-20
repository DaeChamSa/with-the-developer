package com.developer.userservice.user.command.domain.repository;

import com.developer.userservice.dto.ResponseBannedUserDTO;
import com.developer.userservice.user.command.domain.aggregate.BannedUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface BannedUserRepository extends JpaRepository<BannedUser, Long> {
    List<ResponseBannedUserDTO> findByBannedDateBefore(LocalDateTime tenDaysAgo);
}
