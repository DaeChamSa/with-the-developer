package com.developer.recruit.command.dto;

import com.developer.recruit.command.entity.Recruit;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class RecruitResponseDTO {

    private long recruitCode;

    private String recruitTitle; // 채용공고 제목

    private String recruitContent; // 채용공고 내용

    private String recruitUrl; // 채용공고 URL

    private LocalDateTime recruitStart; // 모집 시작일

    private LocalDateTime recruitEnd; // 모집 마감일

    public RecruitResponseDTO(Recruit recruit) {
        this.recruitCode = recruit.getRecruitCode();
        this.recruitTitle = recruit.getRecruitTitle();
        this.recruitContent = recruit.getRecruitContent();
        this.recruitUrl = recruit.getRecruitUrl();
        this.recruitStart = recruit.getRecruitStart();
        this.recruitEnd = recruit.getRecruitEnd();
    }

    private boolean isEndAfterStart() {
        return recruitEnd.isAfter(recruitStart);
    }
}
