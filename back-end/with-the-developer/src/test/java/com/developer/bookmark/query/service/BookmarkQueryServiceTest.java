package com.developer.bookmark.query.service;

import com.developer.bookmark.command.application.dto.BookmarkRegistDTO;
import com.developer.bookmark.command.application.service.BookmarkCommandService;
import com.developer.bookmark.query.dto.BookmarkListDTO;
import com.developer.team.post.command.dto.TeamPostRegistDTO;
import com.developer.team.post.command.service.TeamPostCommandService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BookmarkQueryServiceTest {

    @Autowired
    private BookmarkQueryService bookmarkQueryService;

    @Autowired
    private BookmarkCommandService bookmarkCommandService;

    @Autowired
    private TeamPostCommandService teamPostCommandService;

    @Test
    @DisplayName("사용자 코드를 통해 사용자의 모든 북마크를 조회할 수 있다.")
    void selectAllBookmarks() throws ParseException {

        // given
        // 테스트를 위한 게시글, 북마크 등록
        TeamPostRegistDTO registDTO = TeamPostRegistDTO.builder()
                .teamPostTitle("testTitle")
                .teamContent("testContent")
                .teamPostDeadLine("2024-10-04 00:00:00")
                .jobTagNames(List.of(new String[]{"백엔드"}))
                .userCode(1L)
                .build();

        Long postCode = teamPostCommandService.registTeamPost(registDTO);

        // 북마크 등록
        BookmarkRegistDTO bookmarkRegistDTO1 = BookmarkRegistDTO.builder()
                .bmkUrl("teamPostUrl1")
                .bmkTitle("testTitle")
                .postType("teamPost")
                .postCode(postCode)
                .userCode(1L)
                .build();

        BookmarkRegistDTO bookmarkRegistDTO2 = BookmarkRegistDTO.builder()
                .bmkUrl("teamPostUrl2")
                .bmkTitle("testTitle")
                .postType("teamPost")
                .postCode(postCode)
                .userCode(1L)
                .build();

        Long bmkCode1 = bookmarkCommandService.registBookmark(bookmarkRegistDTO1);
        Long bmkCode2 = bookmarkCommandService.registBookmark(bookmarkRegistDTO2);

        // when
        List<BookmarkListDTO> bookmarkList = bookmarkQueryService.selectBookmarkByUserCode(1L);

        // then
        assertEquals(2, bookmarkList.size());

        bookmarkCommandService.deleteBookmark(bmkCode1, 1L);
        bookmarkCommandService.deleteBookmark(bmkCode2, 1L);

    }

}