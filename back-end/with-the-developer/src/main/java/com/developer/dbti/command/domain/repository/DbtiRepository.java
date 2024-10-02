package com.developer.dbti.command.domain.repository;

import com.developer.dbti.command.domain.aggregate.Dbti;

import java.util.List;
import java.util.Optional;

public interface DbtiRepository {

    Dbti save(Dbti dbti);

    Optional<Dbti> findById(Long dbtiCode);

    void delete(Dbti dbti);

    void deleteById(Long id);

    void deleteByDbtiValue(String dbtiValue);

    List<Dbti> findAll();
}
