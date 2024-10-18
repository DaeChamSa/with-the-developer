package com.developer.msgservice.msg.command.infrastructure.repository;

import com.developer.msgservice.msg.command.domain.aggregate.ResMsg;
import com.developer.msgservice.msg.command.domain.repository.ResMsgRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaResMsgRepository extends JpaRepository<ResMsg, Long>, ResMsgRepository {
}
