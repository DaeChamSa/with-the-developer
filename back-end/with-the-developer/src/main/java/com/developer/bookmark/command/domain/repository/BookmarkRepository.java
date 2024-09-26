package com.developer.bookmark.command.domain.repository;

import com.developer.bookmark.command.domain.aggregate.Bookmark;


public interface BookmarkRepository {

    Bookmark save(Bookmark bookmark);
}
