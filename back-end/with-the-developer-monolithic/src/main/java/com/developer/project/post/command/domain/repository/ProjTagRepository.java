package com.developer.project.post.command.domain.repository;

import com.developer.project.post.command.domain.aggregate.ProjTag;
import org.springframework.data.jpa.repository.JpaRepository;



public interface ProjTagRepository extends JpaRepository<ProjTag, Long> {
}
