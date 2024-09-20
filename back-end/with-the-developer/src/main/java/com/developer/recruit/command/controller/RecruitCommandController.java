package com.developer.recruit.command.controller;

import com.developer.recruit.command.dto.RecruitResponseDTO;
import com.developer.recruit.command.service.RecruitCommandService;
import com.developer.recruit.command.dto.RecruitApplyDTO;
import com.developer.user.command.dto.SessionSaveDTO;
import com.developer.user.command.entity.User;
import com.developer.user.command.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
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
    private final UserRepository userRepository;

    // 채용공고 등록 신청
    @PostMapping("/apply")
    public ResponseEntity<String> applyRecruit(
            @RequestBody RecruitApplyDTO newApplyRecruitDTO,
            HttpServletRequest request) {

        SessionSaveDTO sessionSaveDTO = (SessionSaveDTO) request.getSession().getAttribute("user");
        // 세션에서 로그인된 userCode로 user 가져오기
        Long userCode = sessionSaveDTO.getUserCode();

        if (userCode == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
        }

        User user = userRepository.findById(userCode)
                        .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 user code입니다."));

        RecruitResponseDTO recruitResponseDTO = recruitService.applyRecruit(newApplyRecruitDTO, user);

        return ResponseEntity
                .created(URI.create("/recruit/apply/" + recruitResponseDTO.getRecruitCode()))
                .build();
    }


}



