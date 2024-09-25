package com.developer.jobTag.repository;

import com.developer.jobTag.entity.JobTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JobTagRepository extends JpaRepository<JobTag, Long> {

    boolean existsByJobTagName(String jobTagName);
    Optional<JobTag> findByJobTagName(String jobTagName);
}
