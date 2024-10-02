package com.developer.admin.command.service;

import com.developer.common.exception.CustomException;
import com.developer.common.exception.ErrorCode;
import com.developer.report.command.entity.ReportReasonCategory;
import com.developer.report.command.repository.ReportReasonCategoryRepository;
import com.developer.jobTag.command.entity.JobTag;
import com.developer.jobTag.command.repository.JobTagRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AdminCommandServiceTest {

    @Autowired
    private AdminCommandService adminCommandService;
    @Autowired
    private JobTagRepository jobTagRepository;
    @Autowired
    private ReportReasonCategoryRepository reportReasonCategoryRepository;

    @Test
    @Transactional
    @DisplayName(value = "직무태그를 등록할 수 있다.")
    void createJobTag() {
        // Given
        String newJobTagName = "새로운 직무태그";

        // When
        adminCommandService.createJobTag(newJobTagName);

        // Then
        List<JobTag> jobTagList = jobTagRepository.findAll();
        boolean existsJobTagName = jobTagRepository.existsByJobTagName(newJobTagName);

        assertEquals(6, jobTagList.size());
        assertTrue(existsJobTagName);
    }

    @Test
    @Transactional
    @DisplayName(value = "존재하는 직무태그를 등록하려 할 경우 에러가 발생한다.")
    void createDuplicatedJobTag() {
        // Given
        String newJobTagName = "직무태그";

        // When
        CustomException exception = assertThrows(CustomException.class, () -> {
            adminCommandService.createJobTag(newJobTagName);
        });

        // Then
        // 현재 DB에는 "직무태그"라는 직무태그가 존재하므로 같은 이름의 직무태그를 등록할 시 에러 발생
        assertEquals(ErrorCode.DUPLICATE_VALUE, exception.getErrorCode());

        // 존재하는 직무태그를 등록하려 하는 경우 에러가 발생하므로 직무태그의 개수는 기존에 존재하던 것과 같아야 한다.
        List<JobTag> jobTagList = jobTagRepository.findAll();
        assertEquals(5, jobTagList.size());
    }

    @Test
    @Transactional
    @DisplayName(value = "null인 직무태그를 등록하려 할 경우 에러가 발생한다.")
    void createNullJobTag() {
        // Given
        String jobTagName = null;

        // When
        CustomException exception = assertThrows(CustomException.class, () -> {
            adminCommandService.createJobTag(jobTagName);
        });
        // Then
        assertEquals(ErrorCode.MISSING_VALUE, exception.getErrorCode());
    }

    @Test
    @Transactional
    @DisplayName(value = "공백인 직무태그를 등록하려 할 경우 에러가 발생한다.")
    void createEmptyJobTag() {
        // Given
        String jobTagName = "   ";

        // When
        CustomException exception = assertThrows(CustomException.class, () -> {
            adminCommandService.createJobTag(jobTagName);
        });
        // Then
        assertEquals(ErrorCode.MISSING_VALUE, exception.getErrorCode());
    }

    @Test
    @Transactional
    @DisplayName(value = "직무태그를 삭제할 수 있다.")
    void deleteJobTag() {
        // Given
        String jobTagName = "직무태그";

        // When
        adminCommandService.deleteJobTag(jobTagName);

        // Then
        List<JobTag> jobTagList = jobTagRepository.findAll();
        assertEquals(4, jobTagList.size());

        boolean existsJobTagName = jobTagRepository.existsByJobTagName(jobTagName);
        assertFalse(existsJobTagName);
    }

    @Test
    @Transactional
    @DisplayName(value = "신고 사유 카테고리를 생성할 수 있다.")
    void createReportReasonCategory() {
        // Given
        String newReportReasonCategory = "부적절한 게시글입니다.";

        // When
        adminCommandService.createReportReasonCategory(newReportReasonCategory);

        // Then
        List<ReportReasonCategory> reportReasonCategories = reportReasonCategoryRepository.findAll();
        boolean existsReportReasonCategory = reportReasonCategoryRepository.existsByRepoReasonName(newReportReasonCategory);

        assertEquals(1, reportReasonCategories.size());
        assertTrue(existsReportReasonCategory);
    }

    @Test
    @Transactional
    @DisplayName(value = "존재하는 신고 카테고리를 등록하려 할 경우 에러가 발생한다.")
    void createDuplicatedReportReasonCategory() {
        // Given
        String newReportReasonCategory = "부적절한 게시물입니다.";

        // When
        CustomException exception = assertThrows(CustomException.class, () -> {
            adminCommandService.createReportReasonCategory(newReportReasonCategory);
        });

        // Then
        // 현재 DB에는 "부적절한 게시물입니다."라는 신고 사유 카테고리가 존재하므로 같은 이름의 신고 사유 카테고리를 등록할 시 에러 발생
        assertEquals(ErrorCode.DUPLICATE_VALUE, exception.getErrorCode());

        // 존재하는 신고 사유 카테고리를 등록하려 하는 경우 에러가 발생하므로 개수는 기존에 존재하던 것과 같아야 한다.
        List<ReportReasonCategory> reportReasonCategoryList = reportReasonCategoryRepository.findAll();
        assertEquals(1, reportReasonCategoryList.size());
    }

    @Test
    @Transactional
    @DisplayName(value = "NULL인 신고 사유 카테고리를 생성하려 할 경우 에러가 발생하다.")
    void createNullReportReasonCategory() {
        // Given
        String nullReportReasonCategory = null;

        // When
        CustomException exception = assertThrows(CustomException.class, () -> {
            adminCommandService.createReportReasonCategory(nullReportReasonCategory);
        });

        // Then
        assertEquals(ErrorCode.MISSING_VALUE, exception.getErrorCode());
    }

    @Test
    @Transactional
    @DisplayName(value = "공백인 신고 사유 카테고리를 생성하려 할 경우 에러가 발생한다.")
    void createEmptyReportReasonCategory() {
        // Given
        String nullReportReasonCategory = "  ";

        // When
        CustomException exception = assertThrows(CustomException.class, () -> {
            adminCommandService.createReportReasonCategory(nullReportReasonCategory);
        });

        // Then
        assertEquals(ErrorCode.MISSING_VALUE, exception.getErrorCode());
    }

    @Test
    @Transactional
    @DisplayName(value = "신고 사유 카테고리를 삭제할 수 있다.")
    void deleteReportReasonCategory() {
        // Given
        String reportReasonCategory = "부적절한 게시물입니다.";

        // When
        adminCommandService.deleteReportReasonCategory(reportReasonCategory);

        // Then
        List<ReportReasonCategory> reportReasonCategories = reportReasonCategoryRepository.findAll();
        assertEquals(0, reportReasonCategories.size());

        boolean existsJobTagName = jobTagRepository.existsByJobTagName(reportReasonCategory);
        assertFalse(existsJobTagName);
    }
}