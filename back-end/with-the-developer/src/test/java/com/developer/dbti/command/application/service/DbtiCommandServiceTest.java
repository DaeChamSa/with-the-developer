package com.developer.dbti.command.application.service;

import com.developer.dbti.command.application.dto.DbtiAddDTO;
import com.developer.dbti.command.domain.aggregate.Dbti;
import com.developer.dbti.command.domain.aggregate.DbtiRole;
import com.developer.dbti.command.domain.repository.DbtiRepository;
import com.developer.dbti.query.dto.ResponseDbtiDTO;
import com.developer.dbti.query.mapper.DbtiMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class DbtiCommandServiceTest {

    @Autowired
    private DbtiRepository dbtiRepository;

    @Autowired
    private DbtiMapper dbtiMapper;

    @Autowired
    private DbtiCommandService dbtiCommandService;

    @Test
    @DisplayName(value = "Dbti 성향을 추가 할 수 있다.")
    void addDbti() {
        // Given
        DbtiAddDTO dbtiAddDTO = new DbtiAddDTO();
        dbtiAddDTO.setValue("Some Value");
        dbtiAddDTO.setDbtiRole("BACKEND");

        // When
        dbtiCommandService.addDbti(dbtiAddDTO);

        // Then
        ResponseDbtiDTO savedDbti = dbtiMapper.findAll().get(8);
        assertEquals("Some Value", savedDbti.getDbtiValue());
        assertEquals(DbtiRole.BACKEND, DbtiRole.valueOf(savedDbti.getDbtiRole()));
    }

    @Test
    @DisplayName(value = "Dbti 성향을 삭제 할 수 있다.")
    void deleteDbti() {
        // Given
        Dbti dbti = new Dbti("깔끔한 삭제", DbtiRole.PM);
        Dbti savedDbti = dbtiRepository.save(dbti);

        // When
        dbtiCommandService.deleteDbti(savedDbti.getDbtiCode());

        // Then
        assertFalse(dbtiRepository.findById(savedDbti.getDbtiCode()).isPresent());

    }
}