package com.developer.jobTag.repository;

import com.developer.jobTag.entity.JobTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobTagRepository extends JpaRepository<JobTag, Long> {
}
