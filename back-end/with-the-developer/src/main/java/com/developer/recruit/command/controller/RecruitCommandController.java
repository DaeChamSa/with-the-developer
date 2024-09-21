package com.developer.recruit.command.controller;

import com.developer.recruit.command.service.RecruitCommandService;
import com.developer.recruit.command.dto.RecruitApplyDTO;
import com.developer.user.command.dto.SessionSaveDTO;
import com.developer.user.command.entity.User;
import com.developer.user.command.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/recruit")
@RequiredArgsConstructor
public class RecruitCommandController {

    private final RecruitCommandService recruitService;

    // 채용공고 등록 신청
    @PostMapping("/apply")
    public ResponseEntity<String> applyRecruit(
            @Valid @RequestBody RecruitApplyDTO newApplyRecruitDTO,
            HttpServletRequest request)
    {
        // 로그인 되어 있는지 체크. 로그인 되어 있지 않으면 에러, 되어 있다면 로그인 되어 있는 회원 userCode 반환
        Long loggedUserCode = recruitService.getLoggedUser(request).getUserCode();

        Long recruitCode = recruitService.applyRecruit(newApplyRecruitDTO, loggedUserCode);

        return ResponseEntity
                .created(URI.create("/recruit/apply/" + recruitCode))
                .build();
    }
}



