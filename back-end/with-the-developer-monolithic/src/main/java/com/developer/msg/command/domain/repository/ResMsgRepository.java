package com.developer.msg.command.domain.repository;

import com.developer.msg.command.domain.aggregate.ResMsg;

import java.util.List;
import java.util.Optional;

public interface ResMsgRepository {

    ResMsg save(ResMsg resMsg);

    Optional<ResMsg> findById(Long msgCode);

    void deleteById(Long msgCode);

    List<ResMsg> findAll();
}
