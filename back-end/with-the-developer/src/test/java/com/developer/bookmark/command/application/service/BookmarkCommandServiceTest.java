package com.developer.bookmark.command.application.service;

import com.developer.bookmark.command.application.dto.BookmarkRegistDTO;
import com.developer.bookmark.command.domain.aggregate.Bookmark;
import com.developer.bookmark.command.domain.repository.BookmarkRepository;
import com.developer.bookmark.query.mapper.BookmarkMapper;
import com.developer.common.exception.CustomException;
import com.developer.common.exception.ErrorCode;
import com.developer.comu.post.command.dto.ComuPostCreateDTO;
import com.developer.comu.post.command.service.ComuPostService;
import com.developer.project.post.command.application.dto.ProjPostRequestDTO;
import com.developer.project.post.command.application.service.ProjPostCommandService;
import com.developer.recruit.command.dto.RecruitApplyDTO;
import com.developer.recruit.command.service.RecruitCommandService;
import com.developer.team.post.command.dto.TeamPostDeleteDTO;
import com.developer.team.post.command.dto.TeamPostRegistDTO;
import com.developer.team.post.command.service.TeamPostCommandService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BookmarkCommandServiceTest {

    @Autowired
    private BookmarkCommandService bookmarkCommandService;

    @Autowired
    private TeamPostCommandService teamPostCommandService;

    @Autowired
    private BookmarkRepository bookmarkRepository;

    @Autowired
    private BookmarkMapper bookmarkMapper;

    @Autowired
    private ComuPostService comuPostService;

    @Autowired
    private ProjPostCommandService projPostCommandService;

    @Autowired
    private RecruitCommandService recruitCommandService;

    private Long userCode = 1L;

    private static Long teamBmkCode;
    private static Long recruitBmkCode;
    private static Long projBmkCode;
    private static Long comuBmkCode;

    @Test
    @Order(1)
    @DisplayName("팀 모집 게시글 북마크를 등록 할 수 있다.")
    void registTeamPostBookmark() throws ParseException {

        // given
        // 테스트를 위한 게시글 등록
        TeamPostRegistDTO registDTO = TeamPostRegistDTO.builder()
                .teamPostTitle("testTitle")
                .teamContent("testContent")
                .teamPostDeadLine("2024-10-04 00:00:00")
                .jobTagNames(List.of(new String[]{"백엔드"}))
                .userCode(userCode)
                .build();

        Long postCode = teamPostCommandService.registTeamPost(registDTO);

        // 북마크 등록
        BookmarkRegistDTO bookmarkRegistDTO = BookmarkRegistDTO.builder()
                .bmkUrl("teamPostUrl")
                .bmkTitle("testTitle")
                .postType("teamPost")
                .postCode(postCode)
                .userCode(userCode)
                .build();

        // when
        teamBmkCode = bookmarkCommandService.registBookmark(bookmarkRegistDTO);

        // then
        Bookmark bookmark = bookmarkRepository.findById(teamBmkCode).orElseThrow(
                () -> new CustomException(ErrorCode.NOT_FOUND_BOOKMARK)
        );

        assertNotNull(bookmark);
        assertEquals(userCode, bookmark.getUserCode());
        assertEquals(bookmarkRegistDTO.getBmkUrl(), bookmark.getBmkUrl());
        assertEquals(bookmarkRegistDTO.getBmkTitle(), bookmark.getBmkTitle());
        assertEquals(bookmarkRegistDTO.getPostCode(), bookmark.getTeamPostCode());

        teamPostCommandService.deleteTeamPost(new TeamPostDeleteDTO(postCode, userCode));
    }

    @Test
    @Order(2)
    @DisplayName("커뮤니티 게시글 북마크를 등록 할 수 있다.")
    void registComuPostBookmark() throws ParseException {

        // given
        // 테스트를 위한 게시글 등록
        ComuPostCreateDTO comuPostCreateDTO = new ComuPostCreateDTO();
        comuPostCreateDTO.setComuSubject("testSubject");
        comuPostCreateDTO.setComuContent("testContent");
        Long postCode = comuPostService.createComuPost(comuPostCreateDTO, "admin");

        // 북마크 등록
        BookmarkRegistDTO bookmarkRegistDTO = BookmarkRegistDTO.builder()
                .bmkUrl("comuPostUrl")
                .bmkTitle("testTitle")
                .postType("comuPost")
                .postCode(postCode)
                .userCode(userCode)
                .build();

        // when
        comuBmkCode = bookmarkCommandService.registBookmark(bookmarkRegistDTO);

        // then
        Bookmark bookmark = bookmarkRepository.findById(comuBmkCode).orElseThrow(
                () -> new CustomException(ErrorCode.NOT_FOUND_BOOKMARK)
        );

        assertNotNull(bookmark);
        assertEquals(userCode, bookmark.getUserCode());
        assertEquals(bookmarkRegistDTO.getBmkUrl(), bookmark.getBmkUrl());
        assertEquals(bookmarkRegistDTO.getBmkTitle(), bookmark.getBmkTitle());
        assertEquals(bookmarkRegistDTO.getPostCode(), bookmark.getComuCode());

        comuPostService.deleteComuPost(postCode, "admin");
    }

    @Test
    @Order(3)
    @DisplayName("프로젝트 자랑 게시글 북마크를 등록 할 수 있다.")
    void registProjPostBookmark(){

        // given
        // 테스트를 위한 게시글 등록
        ProjPostRequestDTO projPostRequestDTO = new ProjPostRequestDTO();
        projPostRequestDTO.setProjPostTitle("testTitle");
        projPostRequestDTO.setProjPostContent("testContent");
        projPostRequestDTO.setProjUrl("projUrl");
        projPostRequestDTO.setProjTagContent(List.of(new String[]{"안드로이드", "스프링부트"}));

        Long postCode = projPostCommandService.createProjPost(userCode, projPostRequestDTO);

        // 북마크 등록
        BookmarkRegistDTO bookmarkRegistDTO = BookmarkRegistDTO.builder()
                .bmkUrl("projPostUrl")
                .bmkTitle("testTitle")
                .postType("projPost")
                .postCode(postCode)
                .userCode(userCode)
                .build();

        // when
        projBmkCode = bookmarkCommandService.registBookmark(bookmarkRegistDTO);

        // then
        Bookmark bookmark = bookmarkRepository.findById(projBmkCode).orElseThrow(
                () -> new CustomException(ErrorCode.NOT_FOUND_BOOKMARK)
        );

        assertNotNull(bookmark);
        assertEquals(userCode, bookmark.getUserCode());
        assertEquals(bookmarkRegistDTO.getBmkUrl(), bookmark.getBmkUrl());
        assertEquals(bookmarkRegistDTO.getBmkTitle(), bookmark.getBmkTitle());
        assertEquals(bookmarkRegistDTO.getPostCode(), bookmark.getProjPostCode());

        projPostCommandService.deleteProjPost(userCode, postCode);
    }

    @Test
    @Order(4)
    @DisplayName("채용공고 북마크를 등록 할 수 있다.")
    void registRecruitBookmark() {

        //given
        // 테스트를 위한 게시글 등록
        RecruitApplyDTO recruitApplyDTO = new RecruitApplyDTO();
        recruitApplyDTO.setRecruitTitle("testTitle");
        recruitApplyDTO.setRecruitContent("testContent");
        recruitApplyDTO.setRecruitUrl("recruitUrl");
        recruitApplyDTO.setRecruitStart(LocalDateTime.parse("2024-09-30T09:00:00"));
        recruitApplyDTO.setRecruitEnd(LocalDateTime.parse("2024-10-01T09:00:00"));
        recruitApplyDTO.setJobTagNames(List.of(new String[]{"백엔드"}));


        Long postCode = recruitCommandService.applyRecruit(recruitApplyDTO, userCode);

        // 북마크 등록
        BookmarkRegistDTO bookmarkRegistDTO = BookmarkRegistDTO.builder()
                .bmkUrl("recruitUrl")
                .bmkTitle("testTitle")
                .postType("recruit")
                .postCode(postCode)
                .userCode(userCode)
                .build();

        // when
        recruitBmkCode = bookmarkCommandService.registBookmark(bookmarkRegistDTO);

        // then
        Bookmark bookmark = bookmarkRepository.findById(recruitBmkCode).orElseThrow(
                () -> new CustomException(ErrorCode.NOT_FOUND_BOOKMARK)
        );

        assertNotNull(bookmark);
        assertEquals(userCode, bookmark.getUserCode());
        assertEquals(bookmarkRegistDTO.getBmkUrl(), bookmark.getBmkUrl());
        assertEquals(bookmarkRegistDTO.getBmkTitle(), bookmark.getBmkTitle());
        assertEquals(bookmarkRegistDTO.getPostCode(), bookmark.getRecruitCode());

        recruitCommandService.deleteRecruit(postCode, userCode);
    }

    @Test
    @Order(5)
    @DisplayName("북마크 고유번호를 통해 북마크를 삭제할 수 있다.")
    void deleteBookmark() throws ParseException {

        // given

        // when
        bookmarkCommandService.deleteBookmark(teamBmkCode, userCode);
        bookmarkCommandService.deleteBookmark(comuBmkCode, userCode);
        bookmarkCommandService.deleteBookmark(projBmkCode, userCode);
        bookmarkCommandService.deleteBookmark(recruitBmkCode, userCode);

        // then
        assertEquals(0,bookmarkMapper.selectBookmarkByUserCode(userCode).size());

    }

    @Test
    @Order(6)
    @DisplayName("북마크의 Url은 중복되선 안된다.")
    void exception() throws ParseException {

        // given
        // 테스트를 위한 게시글 등록
        TeamPostRegistDTO registDTO = TeamPostRegistDTO.builder()
                .teamPostTitle("testTitle")
                .teamContent("testContent")
                .teamPostDeadLine("2024-10-04 00:00:00")
                .jobTagNames(List.of(new String[]{"백엔드"}))
                .userCode(userCode)
                .build();

        Long postCode = teamPostCommandService.registTeamPost(registDTO);

        // 북마크 등록
        BookmarkRegistDTO bookmarkRegistDTO1 = BookmarkRegistDTO.builder()
                .bmkUrl("teamPostUrl")
                .bmkTitle("testTitle1")
                .postType("teamPost1")
                .postCode(postCode)
                .userCode(userCode)
                .build();

        // 중복 북마크 등록
        BookmarkRegistDTO bookmarkRegistDTO2 = BookmarkRegistDTO.builder()
                .bmkUrl("teamPostUrl")
                .bmkTitle("testTitle2")
                .postType("teamPost2")
                .postCode(postCode)
                .userCode(userCode)
                .build();

        teamBmkCode = bookmarkCommandService.registBookmark(bookmarkRegistDTO1);

        // when
        CustomException exception = assertThrows(
                CustomException.class, () -> {
                    teamBmkCode = bookmarkCommandService.registBookmark(bookmarkRegistDTO2);
                }
        );

        // then
        assertEquals(ErrorCode.DUPLICATE_BOOKMARK, exception.getErrorCode());
        bookmarkCommandService.deleteBookmark(teamBmkCode, userCode);
    }

}