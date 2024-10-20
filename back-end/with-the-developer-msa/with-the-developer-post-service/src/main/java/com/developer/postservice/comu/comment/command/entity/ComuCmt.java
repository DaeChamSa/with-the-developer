package com.developer.postservice.comu.comment.command.entity;

import com.developer.postservice.common.util.BaseEntity;
import com.developer.postservice.common.util.BaseEntity;
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

    private Long userCode;

    public ComuCmt() {
    }

    public ComuCmt(Long comuPostCode, Long userCode, String comuCmtContent) {
        this.comuPostCode = comuPostCode;
        this.userCode = userCode;
        this.comuCmtContent = comuCmtContent;
    }

    public void createComuCmt(String comuCmtContent) {
        this.comuCmtContent = comuCmtContent;
    }

    public void updateComuCmt(String comuCmtContent) {
        this.comuCmtContent = comuCmtContent;
    }
}
