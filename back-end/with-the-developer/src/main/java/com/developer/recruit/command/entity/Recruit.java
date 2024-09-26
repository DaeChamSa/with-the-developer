package com.developer.recruit.command.entity;

import com.developer.admin.command.dto.AdminRecruitApplyUpdateDTO;
import com.developer.jobTag.entity.RecruitTag;
import com.developer.user.command.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "recruit")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Recruit {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recruitCode; // 채용공고 코드

    @NotNull
    private String recruitTitle; // 채용공고 제목

    @NotNull
    private String recruitContent; // 채용공고 내용

    private String recruitUrl; // 채용공고 URL

    @NotNull
    private LocalDateTime recruitStart; // 모집 시작일

    @NotNull
    private LocalDateTime recruitEnd; // 모집 마감일

    @Enumerated(EnumType.STRING)
    @NotNull
    private ApprStatus recruitApprStatus; // 승인 상태

    @CreationTimestamp
    @NotNull
    private LocalDateTime recruitApplyDate; // 채용공고 신청 날짜

    private LocalDateTime recruitPostDate; // 채용공고 게시 날짜

    @Enumerated(EnumType.STRING)
    private RecruitStatus recruitStatus; // 채용공고 상태

    @ManyToOne
    @JoinColumn(name = "userCode")
    private User user;

    @OneToMany(mappedBy = "recruit", cascade = CascadeType.ALL)
    private List<RecruitTag> recruitTags = new ArrayList<>();

    @Builder
    public Recruit(String recruitTitle, String recruitContent, String recruitUrl, LocalDateTime recruitStart, LocalDateTime recruitEnd) {
        this.recruitTitle = recruitTitle;
        this.recruitContent = recruitContent;
        this.recruitUrl = recruitUrl;
        this.recruitStart = recruitStart;
        this.recruitEnd = recruitEnd;
        this.recruitApprStatus = ApprStatus.WAITING;
    }

    public void updateUser(User user) {
        this.user = user;
    }

    public void addRecruitTag(RecruitTag recruitTag) {
        // recruitTags 리스트에 recruitTag 객체 추가
        this.recruitTags.add(recruitTag);
        recruitTag.updateRecruit(this);
    }

    // 채용공고 등록 신청 승인
    public void updateRecruitApply(AdminRecruitApplyUpdateDTO adminRecruitApplyUpdateDTO) {
        this.recruitApprStatus = adminRecruitApplyUpdateDTO.getRecruitApprStatus();
        this.recruitPostDate = adminRecruitApplyUpdateDTO.getRecruitPostDate();
        this.recruitStatus = adminRecruitApplyUpdateDTO.getRecruitStatus();
    }

    // 채용공고 마감
    public void completeRecruit() {
        this.recruitStatus = RecruitStatus.COMPLETED;
    }

    // 채용공고 삭제
    public void deleteRecruit() {
        this.recruitStatus = RecruitStatus.DELETE;
    }
}
