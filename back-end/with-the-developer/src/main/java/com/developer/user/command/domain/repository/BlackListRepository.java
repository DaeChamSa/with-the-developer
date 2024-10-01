package com.developer.user.command.domain.repository;

import com.developer.user.command.domain.aggregate.BlackList;

public interface BlackListRepository {

    // 블랙리스트 저장
    BlackList save(BlackList blackList);
}
