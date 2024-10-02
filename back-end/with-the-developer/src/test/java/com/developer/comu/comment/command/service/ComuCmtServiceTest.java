package com.developer.comu.comment.command.service;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ComuCmtServiceTest {

    @Autowired
    private ComuCmtService omuCmtService;

    @Test
    @DisplayName("커뮤니티 게시글 등록 테스트")
    void createComuCmtTest() {}

}