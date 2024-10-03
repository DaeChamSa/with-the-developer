package com.developer.msg.command.infrastructure.repository;

import com.developer.msg.command.domain.aggregate.ReqMsg;
import com.developer.msg.command.domain.repository.ReqMsgRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaReqMsgRepository extends JpaRepository<ReqMsg, Long>, ReqMsgRepository {
}
