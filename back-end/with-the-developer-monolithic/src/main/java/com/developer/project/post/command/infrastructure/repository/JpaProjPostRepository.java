package com.developer.project.post.command.infrastructure.repository;

import com.developer.project.post.command.domain.aggregate.ProjPost;
import com.developer.project.post.command.domain.repository.ProjPostRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaProjPostRepository extends ProjPostRepository, JpaRepository<ProjPost, Long> {
}
