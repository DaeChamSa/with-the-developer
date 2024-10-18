package com.developer.user.query.mapper;

import com.developer.user.command.domain.aggregate.BlackList;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface BlackListMapper {

    Optional<BlackList> findByAccessToken(String accessToken);
}
