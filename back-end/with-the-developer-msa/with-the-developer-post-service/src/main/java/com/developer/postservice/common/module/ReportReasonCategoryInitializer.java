package com.developer.postservice.common.module;

import com.developer.postservice.report.command.entity.ReportReasonCategory;
import com.developer.postservice.report.command.repository.ReportReasonCategoryRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
@Slf4j
@Component
@RequiredArgsConstructor
public class ReportReasonCategoryInitializer {
    private final ReportReasonCategoryRepository reportReasonCategoryRepository;
    @PostConstruct
    public void init() {
        if (reportReasonCategoryRepository.findAll().isEmpty()) {
            List<ReportReasonCategory> reportReasonCategories = Arrays.asList(
                    new ReportReasonCategory("부적절한 컨텐츠입니다."),
                    new ReportReasonCategory("스팸 및 광고입니다."),
                    new ReportReasonCategory("서비스 규칙 위반입니다."),
                    new ReportReasonCategory("기타")
            );
            reportReasonCategoryRepository.saveAll(reportReasonCategories);
            log.info("Report Reason Category 초기 저장 성공 {}", reportReasonCategories);
        }
    }
}
