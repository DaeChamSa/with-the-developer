package com.developer.bookmark.command.application.controller;

import com.developer.bookmark.command.application.dto.BookmarkRegistDTO;
import com.developer.bookmark.command.application.service.BookmarkCommandService;
import com.developer.common.module.PostAndBookmarkService;
import com.developer.common.success.SuccessCode;
import com.developer.user.security.SecurityUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/bookmark")
public class BookmarkCommandController {

    private final PostAndBookmarkService postAndBookmarkService;

    @PostMapping("/regist")
    public ResponseEntity<SuccessCode> registBookmark(
            @Valid @RequestBody BookmarkRegistDTO bookmarkRegistDTO
    ){
        Long loginUser = SecurityUtil.getCurrentUserCode();

        bookmarkRegistDTO.setUserCode(loginUser);
        // 게시글 타입에 따라 게시글이 존재하는지 조회하고 북마크 생성하는 서비스
        postAndBookmarkService.bookmarkRegistByPostType(bookmarkRegistDTO);

        return ResponseEntity.ok(SuccessCode.BOOKMARK_CREATE_OK);
    }
}
