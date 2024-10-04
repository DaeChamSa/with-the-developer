package com.developer.jobTag.command.repository;

import com.developer.jobTag.command.entity.JobTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JobTagRepository extends JpaRepository<JobTag, Long> {

    boolean existsByJobTagName(String jobTagName);
    Optional<JobTag> findByJobTagName(String jobTagName);
    List<JobTag> findAllByJobTagNameIn(List<String> jobTagNames);
    void deleteByJobTagName(String jobTagName);
}
