package com.developer.teampost.command.service;

import com.developer.teampost.command.dto.TeamPostRegisterDTO;
import com.developer.teampost.command.entity.TeamPost;
import com.developer.teampost.command.repository.TeamPostRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


@Service
@RequiredArgsConstructor
public class TeamPostService {

    private final TeamPostRepository teamPostRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public void registTeamPost(TeamPostRegisterDTO teamDTO) throws ParseException { // 팀 모집 게시글 생성

        Date deadline = convertStringToDate(teamDTO.getTeamPostDeadLine()); // 문자열 값으로 받아온 마감일 Date 타입으로 포맷
        TeamPost teamPost = new TeamPost(teamDTO, deadline);
        teamPostRepository.save(teamPost);
    }

    public Date convertStringToDate(String dateString) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.parse(dateString);
    }

}
