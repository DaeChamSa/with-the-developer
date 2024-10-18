package com.developer.postservice.project.comment.command.infrastructure.repository;

import com.developer.postservice.project.comment.command.domain.aggregate.ProjCmt;
import com.developer.postservice.project.comment.command.domain.repository.ProjCmtRepository;
import com.developer.postservice.project.comment.command.domain.aggregate.ProjCmt;
import com.developer.postservice.project.comment.command.domain.repository.ProjCmtRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaProjCmtRepository extends ProjCmtRepository, JpaRepository<ProjCmt, Long> {
}
