package com.developer.bookmark.query.controller;

import com.developer.bookmark.query.dto.BookmarkListDTO;
import com.developer.bookmark.query.service.BookmarkQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "bookmark", description = "북마크 API")
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/bookmark")
public class BookmarkQueryController {

    private final BookmarkQueryService bookmarkQueryService;

    @GetMapping("/{userCode}")
    @Operation(summary = "북마크 조회", description = "등록한 북마크를 조회합니다.")
    public ResponseEntity<List<BookmarkListDTO>> selectAllBookmark(@PathVariable(name = "userCode") Long userCode) {

        List<BookmarkListDTO> bookmarkList = bookmarkQueryService.selectBookmarkByUserCode(userCode);

        return ResponseEntity.ok(bookmarkList);
    }
}
