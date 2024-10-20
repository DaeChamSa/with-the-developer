package com.developer.postservice.bookmark.command.domain.repository;

import com.developer.postservice.bookmark.command.domain.aggregate.Bookmark;
import com.developer.postservice.bookmark.command.domain.aggregate.Bookmark;

import java.util.List;
import java.util.Optional;

public interface BookmarkRepository {

    Bookmark save(Bookmark bookmark);
    void deleteById(Long bookmarkCode);
    Optional<Bookmark> findById(Long bookmarkCode);
    List<Bookmark> findByUserCodeAndBmkUrl(Long userCode, String bmkUrl);
}
