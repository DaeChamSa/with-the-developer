package com.developer.postservice.dbti.command.infrastructure.repository;

import com.developer.postservice.dbti.command.domain.aggregate.Dbti;
import com.developer.postservice.dbti.command.domain.repository.DbtiRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaDbtiRepository extends DbtiRepository, JpaRepository<Dbti, Long> {
}
