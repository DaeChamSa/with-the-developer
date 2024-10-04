package com.developer.msg.command.domain.aggregate;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.SQLDelete;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "req_msg")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@SQLDelete(sql = "UPDATE req_msg SET del_status = true WHERE msg_code = ?")
public class ReqMsg {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long msgCode;

    @Column(name = "msg_content", nullable = false)
    private String msgContent;

    @CreatedDate
    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;

    @ColumnDefault("false")
    @Column(name = "del_status", nullable = false)
    private boolean delStatus;

    @Column(name = "user_code", nullable = false)
    private Long userCode;

    @Builder
    public ReqMsg(String msgContent) {
        this.msgContent = msgContent;
    }

    public void updateSender(Long userCode) {
        this.userCode = userCode;
    }
}
