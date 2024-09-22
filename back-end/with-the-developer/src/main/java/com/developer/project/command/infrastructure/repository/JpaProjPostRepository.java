package com.developer.project.command.infrastructure.repository;

import com.developer.project.command.domain.aggregate.ProjPost;
import com.developer.project.command.domain.repository.ProjPostRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaProjPostRepository extends ProjPostRepository, JpaRepository<ProjPost, Long> {
}
