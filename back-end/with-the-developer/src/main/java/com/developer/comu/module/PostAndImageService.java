package com.developer.comu.module;

import com.developer.comu.command.service.ComuPostService;
import com.developer.image.command.service.ImageService;
import com.developer.project.comment.command.application.service.ProjCmtCommandService;
import com.developer.recruit.command.service.RecruitCommandService;
import com.developer.teampost.command.dto.TeamPostDeleteDTO;
import com.developer.teampost.command.dto.TeamPostRegistDTO;
import com.developer.teampost.command.dto.TeamPostUpdateDTO;
import com.developer.teampost.command.service.TeamPostCommandService;

import com.developer.teampost.query.dto.TeamPostDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostAndImageService {

    private final ImageService imageService;
    private final TeamPostCommandService teamPostCommandService;
    private final ComuPostService comuPostService;
    private final ProjCmtCommandService projCmtCommandService;
    private final RecruitCommandService recruitCommandService;

    @Transactional
    public Long teamPostUpload(TeamPostRegistDTO teamPostDTO, MultipartFile[] images) throws ParseException, IOException {
        // 서비스 메소드 호출
        Long createdCode = teamPostCommandService.registTeamPost(teamPostDTO);

        // 이미지도 같이 등록 할 경우 ImageService 호출
        if(images!=null && images.length>0) {
            imageService.upload(images, "teamPost", createdCode);
        }

        return createdCode;
    }

    @Transactional
    public void teamPostUpdate(TeamPostUpdateDTO teamPostUpdateDTO, MultipartFile[] images) throws ParseException, IOException {

        teamPostCommandService.updateTeamPost(teamPostUpdateDTO);

        imageService.updateImage(images,"teamPost",teamPostUpdateDTO.getTeamPostCode());

    }

    @Transactional
    public void teamPostDelete(TeamPostDeleteDTO teamPostDTO) throws ParseException {

        imageService.deleteImage("teamPost", teamPostDTO.getTeamPostCode());

        teamPostCommandService.deleteTeamPost(teamPostDTO);
    }


}
