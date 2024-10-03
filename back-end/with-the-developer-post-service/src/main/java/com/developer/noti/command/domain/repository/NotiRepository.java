package com.developer.noti.command.domain.repository;

import com.developer.noti.command.domain.aggregate.Noti;

import java.util.Optional;

public interface NotiRepository {

    Noti save(Noti noti);

    Optional<Noti> findByNotiCodeAndUserCode(Long notiCode, Long userCode);
}
