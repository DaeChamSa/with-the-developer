package com.developer.postservice.bookmark.query.service;

import com.developer.postservice.bookmark.query.dto.BookmarkListDTO;
import com.developer.postservice.bookmark.query.mapper.BookmarkMapper;
import com.developer.postservice.bookmark.query.dto.BookmarkListDTO;
import com.developer.postservice.bookmark.query.mapper.BookmarkMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookmarkQueryService {

    private final BookmarkMapper bookmarkMapper;

    public List<BookmarkListDTO> selectBookmarkByUserCode(Long userCode) {

        return bookmarkMapper.selectBookmarkByUserCode(userCode);
    }
}
