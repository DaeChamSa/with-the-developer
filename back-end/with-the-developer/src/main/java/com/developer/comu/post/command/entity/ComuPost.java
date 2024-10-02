package com.developer.comu.post.command.entity;

import com.developer.common.util.BaseEntity;
import com.developer.user.command.domain.aggregate.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;

@Entity(name="comu_post")
@Table(name="comu_post")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE comu_post SET del_status = true WHERE comu_code = ?")
public class ComuPost extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long comuCode;

    private String comuSubject; //커뮤니티 제목

    private String comuContent; //커뮤니티 내용

    @Getter
    @ManyToOne
    @JoinColumn(name="userCode")
    private User user;

    @Builder
    public ComuPost(Long comuCode, String comuSubject, String comuContent) {
        this.comuCode = comuCode;
        this.comuSubject = comuSubject;
        this.comuContent = comuContent;
    }

    public void updateUser(User user) {
        this.user = user;
    }

    // 글 업데이트 위한 메서드
    public void updateComuPost(String comuSubject, String comuContent) {
        this.comuSubject = comuSubject;
        this.comuContent = comuContent;
    }
}
