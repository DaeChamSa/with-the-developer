package com.developer.bookmark.command.domain.aggregate;


import com.developer.bookmark.command.application.dto.BookmarkRegistDTO;
import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "bookmark")
@Getter
@EntityListeners(AuditingEntityListener.class)
public class Bookmark {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bmkCode;
    @Column(nullable = false)
    private String bmkUrl;
    @Column(nullable = false)
    private String bmkTitle;
    @CreatedDate
    private LocalDateTime bmkCreateDate;

    @Column(nullable = false)
    private Long userCode;
    private Long projPostCode;
    private Long teamPostCode;
    private Long comuCode;
    private Long recruitCode;

    public void setBookmarkByPostType(BookmarkRegistDTO bookmarkRegistDTO){

        this.bmkUrl = bookmarkRegistDTO.getBmkUrl();
        this.bmkTitle = bookmarkRegistDTO.getBmkTitle();
        this.userCode = bookmarkRegistDTO.getUserCode();

        switch (bookmarkRegistDTO.getPostType()){
            case "teamPost" : this.teamPostCode=bookmarkRegistDTO.getPostCode(); break;
            case "projPost" : this.projPostCode=bookmarkRegistDTO.getPostCode(); break;
            case "comuPost" : this.comuCode=bookmarkRegistDTO.getPostCode(); break;
            case "recruit" : this.recruitCode=bookmarkRegistDTO.getPostCode(); break;
        }
    }

}
