package com.developer.bookmark.command.infrastructure.repository;

import com.developer.bookmark.command.domain.aggregate.Bookmark;
import com.developer.bookmark.command.domain.repository.BookmarkRepository;
import org.springframework.data.jpa.repository.JpaRepository;



public interface JpaBookmarkRepository extends BookmarkRepository, JpaRepository<Bookmark, Long> {
}
