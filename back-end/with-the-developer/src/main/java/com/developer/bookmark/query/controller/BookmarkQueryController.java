package com.developer.bookmark.query.controller;

import com.developer.bookmark.query.dto.BookmarkListDTO;
import com.developer.bookmark.query.service.BookmarkQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/bookmark")
public class BookmarkQueryController {

    private final BookmarkQueryService bookmarkQueryService;

    @GetMapping("/{userCode}")
    public ResponseEntity<List<BookmarkListDTO>> selectAllBookmark(@PathVariable(name = "userCode") Long userCode) {

        List<BookmarkListDTO> bookmarkList = bookmarkQueryService.selectBookmarkByUserCode(userCode);

        return ResponseEntity.ok(bookmarkList);
    }
}
