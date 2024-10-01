package com.developer.noti.command.domain.aggregate;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Getter
@Table(name = "noti")
@Entity
@NoArgsConstructor
public class Noti {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "noti_code", nullable = false)
    private Long notiCode;

    @Column(name = "noti_title", nullable = false)
    private String notiTitle;   // 알림 제목

    @Column(name = "noti_read", nullable = false)
    private boolean notiRead;    // 알림 읽음 여부

    @Column(name = "noti_url", nullable = false)
    private String notiUrl;     // 알림링크

    @CreatedDate
    @Column(name = "noti_create_date", nullable = false)
    private LocalDateTime notiCreateDate;   // 생성날짜

    @Column(name = "noit_del_status", nullable = false)
    @ColumnDefault("false")
    private boolean notiDelStatus;      // 삭제 유무

    // 알림을 받은 사용자
    @JoinColumn(name = "user_code")
    private Long userCode;

    /* === 팀모집 연관매핑 === */

    // 팀모집 게시글
    @JoinColumn(name = "team_post_code")
    private Long teamPostCode;

    /* === 채용공고 연관매핑 ===*/

    // 채용공고 게시글
    @JoinColumn(name = "recruit_code")
    private Long recruitCode;

    /* === 프로젝트 연관매핑 === */

    // 프로젝트 자랑 게시글
    @JoinColumn(name = "proj_post_code")
    private Long projPostCode;

    // 프로젝트 자랑 게시글 댓글
    @JoinColumn(name = "proj_cmt_code")
    private Long projCmtCode;

    /* ===커뮤니티 연관매핑=== */
    // 커뮤니티 게시글
    @JoinColumn(name = "comu_code")
    private Long comuCode;

    // Post 일때의 생성자
    public Noti(String notiTitle, Long userCode, Long postCode, String postType) {
        this.notiTitle = notiTitle;
        this.notiUrl = postType + "/detail" + "/" + postCode;
        this.userCode = userCode;
        this.notiRead = false;
        this.notiCreateDate = LocalDateTime.now();
    }

    // 그 외 알림 생성자 (채용공고 승인, 쪽지)
    public Noti(String notiTitle, String notiUrl, Long userCode) {
        this.notiTitle = notiTitle;
        this.notiUrl = notiUrl;
        this.userCode = userCode;
        this.notiRead = false;
        this.notiCreateDate = LocalDateTime.now();
    }


    /* 서비스 로직 생성 */
    // 알림 읽음처리
    public void readNoti(){
        this.notiRead = true;
    }

    // 알림 삭제처리
    public void deleteNoti(){
        this.notiDelStatus = true;
    }
}
