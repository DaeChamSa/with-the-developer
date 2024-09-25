package com.developer.report.command.entity;

import com.developer.comu.command.entity.ComuPost;
import com.developer.project.post.command.domain.aggregate.ProjPost;
import com.developer.recruit.command.entity.ApprStatus;
import com.developer.recruit.command.entity.Recruit;
import com.developer.teampost.command.entity.TeamPost;
import com.developer.user.command.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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
    private ApprStatus reopoStatus;

    @UpdateTimestamp
    private LocalDateTime repoResolveDate;

    @ManyToOne
    @JoinColumn(name = "repoReasonCategory")
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
}
