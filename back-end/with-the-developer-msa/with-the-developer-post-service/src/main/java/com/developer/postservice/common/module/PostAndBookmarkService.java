package com.developer.postservice.common.module;

import com.developer.postservice.bookmark.command.application.dto.BookmarkRegistDTO;
import com.developer.postservice.bookmark.command.application.service.BookmarkCommandService;
import com.developer.postservice.common.exception.CustomException;
import com.developer.postservice.common.exception.ErrorCode;
import com.developer.postservice.comu.post.query.service.ComuPostQueryService;
import com.developer.postservice.common.exception.CustomException;
import com.developer.postservice.common.exception.ErrorCode;
import com.developer.postservice.project.post.query.service.ProjPostQueryService;
import com.developer.postservice.recruit.query.service.RecruitQueryService;
import com.developer.postservice.team.post.query.service.TeamPostQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostAndBookmarkService {

    private final BookmarkCommandService bookmarkCommandService;
    private final TeamPostQueryService teamPostQueryService;
    private final ComuPostQueryService comuPostQueryService;
    private final RecruitQueryService recruitQueryService;
    private final ProjPostQueryService projPostQueryService;

    @Transactional
    public void bookmarkRegistByPostType(BookmarkRegistDTO bookmarkRegistDTO){

        switch (bookmarkRegistDTO.getPostType()){
            case "teamPost" : teamPostQueryService.selectByTeamPostCode(bookmarkRegistDTO.getPostCode()); break;
            case "projPost" : projPostQueryService.readByCode(bookmarkRegistDTO.getPostCode()); break;
            case "comuPost" : comuPostQueryService.selectComuPostByCode(bookmarkRegistDTO.getPostCode()); break;
            // 채용공고 서비스 로직에는 게시글 삭제 여부 체크가 존재하지 않아 삽입
            case "recruit" :
                if( recruitQueryService.readRecruitDetailById(bookmarkRegistDTO.getPostCode()) == null ){
                    throw new CustomException(ErrorCode.NOT_FOUND_POST);
                }
                break;
        }

        bookmarkCommandService.registBookmark(bookmarkRegistDTO);
    }

}
