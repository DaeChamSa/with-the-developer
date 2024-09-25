package com.developer.project.comment.command.domain.aggregate;

import com.developer.common.util.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;

@SQLDelete(sql = "UPDATE proj_cmt SET del_status = true WHERE proj_cmt_code = ?")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class ProjCmt extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long projCmtCode;

    private String projCmtContent;

    private Long projPostCode;

    private Long userCode;

    @Builder
    public ProjCmt(String projCmtContent, Long projPostCode, Long userCode) {
        this.projCmtContent = projCmtContent;
        this.projPostCode = projPostCode;
        this.userCode = userCode;
    }

    public void updateUserAndPost(Long projPostCode, Long userCode) {
        this.projPostCode = projPostCode;
        this.userCode = userCode;
    }

    public void updateCmt(String projCmtContent) {
        this.projCmtContent = projCmtContent;
    }
}
