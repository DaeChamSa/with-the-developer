package com.developer.recruit.command.dto;

import com.developer.recruit.command.entity.Recruit;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
public class RecruitApplyDTO {

    @NotNull(message = "채용공고 제목은 필수입니다.")
    @NotBlank(message = "채용공고 제목은 공백일 수 없습니다.")
    private String recruitTitle; // 채용공고 제목

    @NotNull(message = "채용공고 내용은 필수입니다.")
    private String recruitContent; // 채용공고 내용

    private String recruitUrl; // 채용공고 URL

    @NotNull(message = "채용공고 모집 시작일은 필수입니다.")
    private LocalDateTime recruitStart; // 모집 시작일

    @NotNull(message = "채용공고 모집 마감일은 필수입니다.")
    private LocalDateTime recruitEnd; // 모집 마감일

    @AssertTrue(message = "모집 마감일은 모집 시작일 이후여야 합니다.")
    private boolean isEndAfterStart() {
        return recruitEnd.isAfter(recruitStart);
    }

    private List<String> jobTagNames;

    // DTO -> Entity
    public Recruit toEntity() {
        return Recruit.builder()
                .recruitTitle(recruitTitle)
                .recruitContent(recruitContent)
                .recruitUrl(recruitUrl)
                .recruitStart(recruitStart)
                .recruitEnd(recruitEnd)
                .build();
    }
}