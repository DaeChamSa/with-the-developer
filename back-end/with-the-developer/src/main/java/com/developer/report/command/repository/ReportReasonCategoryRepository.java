package com.developer.report.command.repository;

import com.developer.report.command.entity.ReportReasonCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportReasonCategoryRepository extends JpaRepository<ReportReasonCategory, Long> {
    boolean existsByrepoReasonName(String category);
}
