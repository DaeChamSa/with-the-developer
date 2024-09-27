package com.developer.team.post.command.entity;

import com.developer.common.util.BaseEntity;
import com.developer.user.command.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;

import java.util.Date;

@Entity
@Getter
@Table(name = "team_post")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE team_post SET del_status = true WHERE team_post_code = ?")
public class TeamPost extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_post_code")
    private Long teamPostCode; // 팀모집 게시글 고유 번호

    @Column(name = "team_post_title", nullable = false)
    private String teamPostTitle; // 팀 모집 게시글 제목

    @Column(name = "team_content", nullable = false)
    private String teamContent; // 팀 모집 게시글 본문

    @Column(name = "team_deadline", nullable = false)
    private Date teamDeadline; // 팀 모집 게시글 마감일

    @ManyToOne
    @JoinColumn(name = "user_code")
    private User user;

    @Builder
    public TeamPost(String teamPostTitle, String teamContent) {
        this.teamPostTitle = teamPostTitle;
        this.teamContent = teamContent;
    }

    public void updateTeamPost(String teamPostTitle, String teamContent) {
        this.teamPostTitle = teamPostTitle;
        this.teamContent = teamContent;
    }

    public void updateDeadline(Date deadline) {
        this.teamDeadline = deadline;
    }

    public void updateUser(User user) {
        this.user = user;
    }
}