package com.developer.comu.comment.command.entity;

import com.developer.common.util.BaseEntity;
import com.developer.user.command.domain.aggregate.User;
import jakarta.persistence.*;
import lombok.Getter;

@Entity(name="comu_cmt")
@Table(name="comu_cmt")
@Getter
public class ComuCmt extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long comuCmtCode;

    private String comuCmtContent;

    @Column(name = "comu_code")
    private long comuPostCode;

    @Getter
    @ManyToOne
    @JoinColumn(name="userCode")
    private User user;

    public ComuCmt() {
    }

    public ComuCmt(Long comuPostCode, User user, String comuCmtContent) {
        this.comuPostCode = comuPostCode;
        this.user = user;
        this.comuCmtContent = comuCmtContent;
    }

    public void createComuCmt(String comuCmtContent) {
        this.comuCmtContent = comuCmtContent;
    }

    public void updateComuCmt(String comuCmtContent) {
        this.comuCmtContent = comuCmtContent;
    }
}
