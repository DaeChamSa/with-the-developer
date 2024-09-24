package com.developer.project.comment.command.infrastructure.repository;

import com.developer.project.comment.command.domain.aggregate.ProjCmt;
import com.developer.project.comment.command.domain.repository.ProjCmtRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaProjCmtRepository extends ProjCmtRepository, JpaRepository<ProjCmt, Long> {
}
