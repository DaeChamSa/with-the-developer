package com.developer.dbti.query.service;

import com.developer.common.exception.CustomException;
import com.developer.common.exception.ErrorCode;
import com.developer.dbti.query.dto.ResponseDbtiDTO;
import com.developer.dbti.query.mapper.DbtiMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class DbtiQueryServiceTest {

    @Autowired
    private DbtiQueryService dbtiQueryService;

    @Test
    @DisplayName("주어진 dbtiRole에 대한 결과를 반환한다.")
    void dbtiResult() {
        // Given
        String dbtiRole = "BACKEND"; // 존재하는 역할을 사용

        // When
        List<String> results = dbtiQueryService.dbtiResult(dbtiRole);

        // Then
        assertNotNull(results);
        assertFalse(results.isEmpty());
    }

    @Test
    @DisplayName("존재하지 않는 dbtiRole에 대해 예외를 던진다.")
    void dbtiResult_invalidRole() {
        // Given
        String dbtiRole = "INVALID_ROLE"; // 존재하지 않는 역할

        // When & Then
        CustomException exception = assertThrows(CustomException.class, () -> {
            dbtiQueryService.dbtiResult(dbtiRole);
        });

        assertEquals(ErrorCode.NOT_MATCH_DBTI_ROLE, exception.getErrorCode());
    }

    @Test
    @DisplayName("모든 Dbti를 반환한다.")
    void findAll() {
        // When
        List<ResponseDbtiDTO> allResults = dbtiQueryService.findAll();

        // Then
        assertNotNull(allResults);
        // 리스트가 비어있지 않은지 확인
        assertFalse(allResults.isEmpty());
    }
}