package com.developer.recruit.command.entity;

import com.developer.admin.command.dto.AdminRecruitApplyUpdateDTO;
import com.developer.recruit.command.dto.RecruitApplyDTO;
import com.developer.user.command.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "recruit")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Recruit {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recruitCode; // 채용공고 코드

    private String recruitTitle; // 채용공고 제목

    private String recruitContent; // 채용공고 내용

    private String recruitUrl; // 채용공고 URL

    private LocalDateTime recruitStart; // 모집 시작일

    private LocalDateTime recruitEnd; // 모집 마감일

    @Enumerated(EnumType.STRING)
    private RecruitApprStatus recruitApprStatus; // 승인 상태

    @CreationTimestamp
    private LocalDateTime recruitApplyDate; // 채용공고 신청 날짜

    private LocalDateTime recruitPostDate; // 채용공고 게시 날짜

    @Enumerated(EnumType.STRING)
    private RecruitStatus recruitStatus; // 채용공고 상태

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "userCode")
    private User user;

    public Recruit(RecruitApplyDTO recruitApplyDTO, User user) {
        this.recruitTitle = recruitApplyDTO.getRecruitTitle();
        this.recruitContent = recruitApplyDTO.getRecruitContent();
        this.recruitUrl = recruitApplyDTO.getRecruitUrl();
        this.recruitStart = recruitApplyDTO.getRecruitStart();
        this.recruitEnd = recruitApplyDTO.getRecruitEnd();
        this.recruitApprStatus = RecruitApprStatus.WAITING;
        this.recruitPostDate = null;
        this.recruitStatus = null;
        this.user = user;
    }

    // 채용공고 등록 신청 승인
    public void updateRecruitApply(AdminRecruitApplyUpdateDTO adminRecruitApplyUpdateDTO) {

        this.recruitApprStatus = adminRecruitApplyUpdateDTO.getRecruitApprStatus();
        this.recruitPostDate = adminRecruitApplyUpdateDTO.getRecruitPostDate();
        this.recruitStatus = adminRecruitApplyUpdateDTO.getRecruitStatus();
    }
}
