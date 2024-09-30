package com.developer.team.comment.command.entity;

import com.developer.common.util.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;

@Entity
@Table(name = "team_cmt")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE team_cmt SET del_status = true WHERE team_cmt_code = ?")
public class TeamCmt extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long teamCmtCode;
    @Column(nullable = false)
    private String teamCmt;
    @Column(nullable = false)
    private Long userCode;
    @Column(nullable = false)
    private Long teamPostCode;

    @Builder
    public TeamCmt(String teamCmt, Long userCode, Long teamPostCode) {
        this.teamCmt = teamCmt;
        this.userCode = userCode;
        this.teamPostCode = teamPostCode;
    }

    public void updateTeamCmt(String teamCmt){
        this.teamCmt = teamCmt;
    }
}
