package com.developer.msg.command.domain.aggregate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.SQLDelete;

@Entity
@Table(name = "res_msg")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@SQLDelete(sql = "UPDATE res_msg SET del_status = true WHERE msg_code = ?")
public class ResMsg {

    @Id
    @Column(name = "msg_code")
    private Long msgCode;

    @ColumnDefault("false")
    @Column(name = "is_read", nullable = false)
    private boolean isRead;

    @ColumnDefault("false")
    @Column(name = "del_status", nullable = false)
    private boolean delStatus;

    @Column(name = "user_code", nullable = false)
    private Long userCode;

    @Builder
    public ResMsg(Long msgCode, Long userCode) {
        this.msgCode = msgCode;
        this.userCode = userCode;
    }

    public void updateRead(boolean isRead) {
        this.isRead = isRead;
    }
}
