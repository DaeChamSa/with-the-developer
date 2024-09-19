package com.developer.teampost.command.entity;

import com.developer.teampost.command.dto.TeamPostRegisterDTO;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Entity
@Getter
@Table(name = "team_post")
public class TeamPost {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_post_code")
    private Long teamPostCode; // 팀모집 게시글 고유 번호

    @Column(name = "team_post_title", nullable = false)
    private String teamPostTitle; // 팀 모집 게시글 제목

    @Column(name = "team_content", nullable = false)
    private String teamContent; // 팀 모집 게시글 본문

    @CreationTimestamp
    @Column(name = "team_create_date")
    private Date teamCreateDate; // 팀 모집 게시글 생성일

    @UpdateTimestamp
    @Column(name = "team_update_date")
    private Date teamUpdateDate; // 팀 모집 게시글 수정일

    @Column(name = "team_deadline", nullable = false)
    private Date teamDeadline; // 팀 모집 게시글 마감일

    @Column(name = "team_del_status", columnDefinition = "TINYINT")
    @ColumnDefault("0")
    private Boolean teamDelStatus; // 팀 모집 게시글 삭제여부

    @Column(name = "user_code", nullable = false)
    private Long userCode;

    public TeamPost(TeamPostRegisterDTO teamDTO, Date teamDeadline) {
        this.teamPostTitle = teamDTO.getTeamPostTitle();
        this.teamContent = teamDTO.getTeamContent();
        this.teamDeadline = teamDeadline;
        this.userCode = teamDTO.getUserCode();
        this.teamDelStatus = false;
    }


    public TeamPost() {

    }
}
