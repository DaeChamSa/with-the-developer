package com.developer.noti.command.infrastructure.repository;

import com.developer.noti.command.domain.aggregate.Noti;
import com.developer.noti.command.domain.repository.NotiRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaNotiRepository extends NotiRepository, JpaRepository<Noti, Long> {
}
