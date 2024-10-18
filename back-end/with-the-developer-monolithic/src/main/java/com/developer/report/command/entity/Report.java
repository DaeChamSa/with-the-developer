package com.developer.report.command.entity;

import com.developer.comu.post.command.entity.ComuPost;
import com.developer.project.post.command.domain.aggregate.ProjPost;
import com.developer.recruit.command.entity.ApprStatus;
import com.developer.recruit.command.entity.Recruit;
import com.developer.team.post.command.entity.TeamPost;
import com.developer.user.command.domain.aggregate.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "report")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long repoCode;

    private String repoDescription;

    @CreationTimestamp
    private LocalDateTime repoCreateDate;

    @Enumerated(EnumType.STRING)
    @NotNull
    private ApprStatus repoStatus = ApprStatus.WAITING;

    private LocalDateTime repoResolveDate;

    @ManyToOne
    @JoinColumn(name = "repoReasonCode")
    private ReportReasonCategory repoReasonCategory;

    @ManyToOne
    @JoinColumn(name = "userCode")
    private User user;

    @ManyToOne
    @JoinColumn(name = "teamPostCode")
    private TeamPost teamPost;

    @ManyToOne
    @JoinColumn(name = "projPostCode")
    private ProjPost projPost;

    @ManyToOne
    @JoinColumn(name = "comuPostCode")
    private ComuPost comuPost;

    @ManyToOne
    @JoinColumn(name = "recruitCode")
    private Recruit recruit;

    @Builder
    public Report(String reportDescription, String reportReasonCategory) {
        this.repoDescription = reportDescription;
    }

    public void updateUser(User user) {
        this.user = user;
    }

    public void updateRepoReasonCategory(ReportReasonCategory repoReasonCategory) {
        this.repoReasonCategory = repoReasonCategory;
    }

    public void updateRepoStatus(ApprStatus repoStatus) {
        this.repoStatus = repoStatus;
    }

    public void updateComuCode(ComuPost comuPost) {
        this.comuPost = comuPost;
    }

    public void updateRecruitCode(Recruit recruit) {
        this.recruit = recruit;
    }

    public void updateTeamPostCode(TeamPost teamPost) {
        this.teamPost = teamPost;
    }

    public void updateProjPostCode(ProjPost projPost) {
        this.projPost = projPost;
    }

    public void updateReportResolveDate() { this.repoResolveDate = LocalDateTime.now(); }
}
