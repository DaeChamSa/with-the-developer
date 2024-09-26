package com.developer.admin.command.dto;

import com.developer.recruit.command.entity.ApprStatus;
import com.developer.recruit.command.entity.RecruitStatus;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor

public class AdminRecruitApplyUpdateDTO {

    private ApprStatus recruitApprStatus; // 승인 상태

    private LocalDateTime recruitPostDate; // 채용공고 게시 날짜

    private RecruitStatus recruitStatus;// 채용공고 상태

    @Builder
    public AdminRecruitApplyUpdateDTO(ApprStatus recruitApprStatus, LocalDateTime recruitPostDate, RecruitStatus recruitStatus) {
        this.recruitApprStatus = recruitApprStatus;
        this.recruitPostDate = recruitPostDate;
        this.recruitStatus = recruitStatus;
    }


}