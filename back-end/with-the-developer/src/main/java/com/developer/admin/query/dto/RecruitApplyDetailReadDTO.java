package com.developer.admin.query.dto;

import com.developer.recruit.command.entity.ApprStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
public class RecruitApplyDetailReadDTO {

    private long recruitCode;

    private String recruitTitle; // 채용공고 제목

    private String recruitContent; // 채용공고 내용

    private String recruitUrl; // 채용공고 URL

    private LocalDateTime recruitStart; // 모집 시작일

    private LocalDateTime recruitEnd; // 모집 마감일

    private ApprStatus recruitApprStatus; // 승인 상태

    private LocalDateTime recruitApplyDate; // 채용공고 신청 날짜

    private String userId; // 신청자 아이디

    private List<String> jobTagNames;
}