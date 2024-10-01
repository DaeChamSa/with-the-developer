package com.developer.msg.command.domain.repository;

import com.developer.msg.command.domain.aggregate.ReqMsg;

import java.util.Optional;

public interface ReqMsgRepository {

    ReqMsg save(ReqMsg reqMsg);

    Optional<ReqMsg> findById(Long msgCode);

    void deleteById(Long msgCode);
}
