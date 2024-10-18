package com.developer.postservice.report.command.repository;

import com.developer.postservice.report.command.entity.ReportReasonCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReportReasonCategoryRepository extends JpaRepository<ReportReasonCategory, Long> {
    Optional<ReportReasonCategory> findByRepoReasonName(String reportReasonCategory);

    boolean existsByRepoReasonName(String category);

    void deleteByRepoReasonName(String category);
}
