package com.developer.project.command.domain.aggregate;

import com.developer.common.util.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;

@SQLDelete(sql = "UPDATE proj_post SET del_status = true WHERE proj_post_code = ?")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class ProjPost extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long projPostCode;

    @Column(nullable = false)
    private String projPostTitle;

    @Column(nullable = false)
    private String projPostContent;

    private String projUrl;

    private Long userCode;

    @Builder
    public ProjPost(Long projPostCode, String projPostTitle, String projPostContent, String projUrl, Long userCode) {
        this.projPostCode = projPostCode;
        this.projPostTitle = projPostTitle;
        this.projPostContent = projPostContent;
        this.projUrl = projUrl;
        this.userCode = userCode;
    }

    public void updateUser(Long userCode) {
        this.userCode = userCode;
    }

    public void updateProjPost(String projPostTitle, String projPostContent, String projUrl) {
        this.projPostTitle = projPostTitle;
        this.projPostContent = projPostContent;
        this.projUrl = projUrl;
    }
}
