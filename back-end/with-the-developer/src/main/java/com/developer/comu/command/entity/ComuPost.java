package com.developer.comu.command.entity;

import com.developer.user.command.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity(name="comu_post")
@Table(name="comu_post")
@Getter
public class ComuPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long comuCode;

    private String comuSubject; //커뮤니티 제목
    private String comuContent; //커뮤니티 내용

    @CreationTimestamp
    private LocalDateTime comuCreateDate; // 커뮤니티 작성 날짜
    @UpdateTimestamp
    private LocalDateTime comuUpdateDate; // 커뮤니티 수정 날짜

    private boolean comuDelStatus=false; // 커뮤니티 삭제 여부


    // user 매핑 구현 예정
//    @ManyToOne
//    @JoinColumn(name="userCode")
//    private User userCode;


    public ComuPost() {
    }

    public ComuPost(String comuSubject, String comuContent) {
        this.comuSubject = comuSubject;
        this.comuContent = comuContent;

    }




}
