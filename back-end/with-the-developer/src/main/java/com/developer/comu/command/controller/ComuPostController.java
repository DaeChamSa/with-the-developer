package com.developer.comu.command.controller;

import com.developer.comu.command.dto.ComuPostCreateDTO;
import com.developer.comu.command.dto.ComuPostUpdateDTO;
import com.developer.comu.command.service.ComuPostService;
import com.developer.user.command.dto.TokenSaveDTO;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comu-post")
public class ComuPostController {

    private final ComuPostService comuPostService;

    // 커뮤니티 게시글 등록
    @PostMapping("/regist")
    public ResponseEntity<Void> createComuPost(
            @RequestBody ComuPostCreateDTO comuPostCreateDTO, HttpServletRequest httpServletRequest) {
        TokenSaveDTO tokenSaveDTO = (TokenSaveDTO) httpServletRequest.getSession().getAttribute("user");
        String userId = tokenSaveDTO.getUserId();
        Long comuPostCode = comuPostService.createComuPost(comuPostCreateDTO, userId);

        URI location = URI.create("/comu-post/" + comuPostCode);

        return ResponseEntity.created(location).build();
    }


    // 커뮤니티 게시글 수정
    @PutMapping("/update")
    public ResponseEntity<Void> updateComuPost(
            @RequestBody ComuPostUpdateDTO comuPostUpdateDTO, HttpServletRequest httpServletRequest) {
        TokenSaveDTO tokenSaveDTO = (TokenSaveDTO) httpServletRequest.getSession().getAttribute("user");
        String userId = tokenSaveDTO.getUserId();

        comuPostService.updateComuPost(comuPostUpdateDTO, userId);

        return ResponseEntity.noContent().build();
    }

    // 커뮤니티 게시글 삭제
    @DeleteMapping("/delete/{comuPostCode}")
    public ResponseEntity<Void> deleteComuPost(@PathVariable Long comuPostCode, HttpServletRequest request) {
        TokenSaveDTO tokenSaveDTO = (TokenSaveDTO) request.getSession().getAttribute("user");
        String userId = tokenSaveDTO.getUserId();

        comuPostService.deleteComuPost(comuPostCode, userId);

        return ResponseEntity.noContent().build();
    }


}
