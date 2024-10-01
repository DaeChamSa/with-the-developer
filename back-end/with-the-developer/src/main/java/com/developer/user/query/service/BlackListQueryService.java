package com.developer.user.query.service;

import com.developer.user.query.mapper.BlackListMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class BlackListQueryService {

    private final BlackListMapper blackListMapper;

    // 블랙리스트로 등록되어있는지 확인
    public boolean findByAccessToken(String accessToken) {
        boolean present = blackListMapper.findByAccessToken(accessToken).isPresent();
        log.info("블랙리스트로 등록되어있는지 확인 {}", present);

        return present;
    }
}
