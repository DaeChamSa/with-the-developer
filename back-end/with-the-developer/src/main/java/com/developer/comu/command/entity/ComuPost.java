package com.developer.comu.command.entity;

import com.developer.common.util.BaseEntity;
import com.developer.user.command.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity(name="comu_post")
@Table(name="comu_post")
@Getter
@SQLDelete(sql = "UPDATE comu_post SET comu_del_status = true WHERE comu_code = ?")
public class ComuPost extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long comuCode;

    private String comuSubject; //커뮤니티 제목
    private String comuContent; //커뮤니티 내용

    private LocalDateTime comuCreateDate; // 커뮤니티 작성 날짜
    private LocalDateTime comuUpdateDate; // 커뮤니티 수정 날짜

    private boolean comuDelStatus=false; // 커뮤니티 삭제 여부

    @Getter
    @ManyToOne
    @JoinColumn(name="userCode")
    private User user;

    public ComuPost() {
    }

    public ComuPost(String comuSubject, String comuContent) {
        this.comuSubject = comuSubject;
        this.comuContent = comuContent;
    }

    public ComuPost(User user, String comuSubject, String comuContent) {
        this.user = user;
        this.comuSubject = comuSubject;
        this.comuContent = comuContent;
    }

    // 글 업데이트 위한 메서드
    public void updateComuPost(String comuSubject, String comuContent) {
        this.comuSubject = comuSubject;
        this.comuContent = comuContent;
    }

}
