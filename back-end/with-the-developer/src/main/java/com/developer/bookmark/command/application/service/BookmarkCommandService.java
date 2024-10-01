package com.developer.bookmark.command.application.service;

import com.developer.bookmark.command.application.dto.BookmarkRegistDTO;
import com.developer.bookmark.command.domain.aggregate.Bookmark;
import com.developer.bookmark.command.domain.repository.BookmarkRepository;
import com.developer.common.exception.CustomException;
import com.developer.common.exception.ErrorCode;
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
    public Long registBookmark(@Valid BookmarkRegistDTO bookmarkRegistDTO) {

        // 중복된 북마크가 있는지 확인
        if(!bookmarkRepository.findByUserCodeAndBmkUrl(bookmarkRegistDTO.getUserCode(), bookmarkRegistDTO.getBmkUrl()).isEmpty()) {
            throw new CustomException(ErrorCode.DUPLICATE_BOOKMARK);
        }

        Bookmark newBookmark = new Bookmark();

        newBookmark.setBookmarkByPostType(bookmarkRegistDTO);

        bookmarkRepository.save(newBookmark);

        return newBookmark.getBmkCode();
    }

    @Transactional
    public void deleteBookmark(Long bookmarkCode, Long loginUser) {

        // 삭제하려는 북마크 존재하는지 확인
        Bookmark bookmark = bookmarkRepository
                .findById(bookmarkCode)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_BOOKMARK));

        // 삭제하려는 북마크가 본인의 북마크인지 확인
        if(!(bookmark.getUserCode().equals(loginUser))) {
            throw new CustomException(ErrorCode.UNAUTHORIZED_USER_BOOKMARK);
        }

        bookmarkRepository.deleteById(bookmarkCode);
    }
}
