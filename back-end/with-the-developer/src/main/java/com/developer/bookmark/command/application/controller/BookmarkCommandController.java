package com.developer.bookmark.command.application.controller;

import com.developer.bookmark.command.application.dto.BookmarkRegistDTO;
import com.developer.bookmark.command.application.service.BookmarkCommandService;
import com.developer.common.module.PostAndBookmarkService;
import com.developer.common.success.SuccessCode;
import com.developer.user.security.SecurityUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "bookmark", description = "북마크 API")
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/bookmark")
public class BookmarkCommandController {

    private final PostAndBookmarkService postAndBookmarkService;
    private final BookmarkCommandService bookmarkCommandService;

    @PostMapping("/regist")
    @Operation(summary = "북마크 등록", description = "새로운 북마크를 등록합니다.")
    public ResponseEntity<SuccessCode> registBookmark(
            @Valid @RequestBody BookmarkRegistDTO bookmarkRegistDTO
    ){
        Long loginUser = SecurityUtil.getCurrentUserCode();

        bookmarkRegistDTO.setUserCode(loginUser);
        // 게시글 타입에 따라 게시글이 존재하는지 조회하고 북마크 생성하는 서비스
        postAndBookmarkService.bookmarkRegistByPostType(bookmarkRegistDTO);

        return ResponseEntity.ok(SuccessCode.BOOKMARK_CREATE_OK);
    }

    @DeleteMapping("/delete/{bookmarkCode}")
    @Operation(summary = "북마크 삭제", description = "등록되어 있는 북마크를 삭제합니다.")
    public ResponseEntity<SuccessCode> deleteBookmark(@PathVariable(name = "bookmarkCode") Long bookmarkCode){

        Long loginUser = SecurityUtil.getCurrentUserCode();

        bookmarkCommandService.deleteBookmark(bookmarkCode, loginUser);

        return ResponseEntity.ok(SuccessCode.BOOKMARK_DELETE_OK);
    }
}
