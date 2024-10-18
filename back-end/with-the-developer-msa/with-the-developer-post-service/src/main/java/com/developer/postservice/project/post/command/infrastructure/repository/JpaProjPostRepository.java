package com.developer.postservice.project.post.command.infrastructure.repository;

import com.developer.postservice.project.post.command.domain.aggregate.ProjPost;
import com.developer.postservice.project.post.command.domain.repository.ProjPostRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaProjPostRepository extends ProjPostRepository, JpaRepository<ProjPost, Long> {
}
