package com.developer.bookmark.command.application.service;

import com.developer.bookmark.command.application.dto.BookmarkRegistDTO;
import com.developer.bookmark.command.domain.aggregate.Bookmark;
import com.developer.bookmark.command.domain.repository.BookmarkRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookmarkCommandService {

    private final BookmarkRepository bookmarkRepository;

    @Transactional
    public void registBookmark(@Valid BookmarkRegistDTO bookmarkRegistDTO) {

        Bookmark newBookmark = new Bookmark();
        newBookmark.setBookmarkByPostType(bookmarkRegistDTO);
        log.info("create: "+ newBookmark.getBmkCreateDate());
        bookmarkRepository.save(newBookmark);
    }
}
