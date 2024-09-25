package com.developer.admin.command.dto;

import com.developer.recruit.command.entity.ApprStatus;
import com.developer.recruit.command.entity.Recruit;
import com.developer.recruit.command.entity.RecruitStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class AdminRecruitApplyUpdateDTO {

    private Long recruitCode; // 채용공고 코드

    private ApprStatus recruitApprStatus; // 승인 상태

    private LocalDateTime recruitPostDate; // 채용공고 게시 날짜

    private RecruitStatus recruitStatus;// 채용공고 상태

    public AdminRecruitApplyUpdateDTO(Recruit recruit) {
        this.recruitCode = recruit.getRecruitCode();
        this.recruitApprStatus = recruit.getRecruitApprStatus();
        this.recruitPostDate = recruit.getRecruitPostDate();
        this.recruitStatus = recruit.getRecruitStatus();
    }
}