package com.developer.notiservice.noti.command.infrastructure.repository;

import com.developer.notiservice.noti.command.domain.aggregate.Noti;
import com.developer.notiservice.noti.command.domain.repository.NotiRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaNotiRepository extends NotiRepository, JpaRepository<Noti, Long> {
}
