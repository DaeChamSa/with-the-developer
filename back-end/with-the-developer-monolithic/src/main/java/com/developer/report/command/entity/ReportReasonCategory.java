package com.developer.report.command.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "report_reason_category")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReportReasonCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long repoReasonCode;

    @NotNull
    private String repoReasonName;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "repoReasonCode")
    private List<Report> reportList;

    public ReportReasonCategory(String repoReasonName) {
        this.repoReasonName = repoReasonName;
    }
}
