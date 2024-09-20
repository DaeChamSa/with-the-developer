package com.developer.teampost.command.service;

import com.developer.common.exception.CustomException;
import com.developer.teampost.command.dto.TeamPostDeleteDTO;
import com.developer.teampost.command.dto.TeamPostRegistDTO;
import com.developer.teampost.command.dto.TeamPostUpdateDTO;
import com.developer.teampost.command.entity.TeamPost;
import com.developer.teampost.command.repository.TeamPostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import static com.developer.common.exception.ErrorCode.NOT_FOUNDED_TEAMPOST;
import static com.developer.common.exception.ErrorCode.NOT_MATCH_USERCODE;


@Slf4j
@Service
@RequiredArgsConstructor
public class TeamPostService {

    private final TeamPostRepository teamPostRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public void registTeamPost(TeamPostRegistDTO teamDTO) throws ParseException { // 팀 모집 게시글 생성


        Date deadline = convertStringToDate(teamDTO.getTeamPostDeadLine()); // 문자열 값으로 받아온 마감일 Date 타입으로 포맷
        TeamPost teamPost = new TeamPost(teamDTO, deadline);
        teamPostRepository.save(teamPost);
    }

    @Transactional
    public void updateTeamPost(TeamPostUpdateDTO teamDTO) throws ParseException {

        Date deadline = convertStringToDate(teamDTO.getTeamPostDeadLine());
        TeamPost foundedTeamPost = findByTeamPostCode(teamDTO.getTeamPostCode());

        if(teamDTO.getUserCode() == foundedTeamPost.getUser().getUserCode()) {
            foundedTeamPost.updateTeamPost(teamDTO,deadline);
            teamPostRepository.save(foundedTeamPost);
        }else{
            throw new CustomException(NOT_MATCH_USERCODE);
        }

    }

    @Transactional
    public void deleteTeamPost(TeamPostDeleteDTO teamDTO) throws ParseException {

        TeamPost foundedTeamPost = findByTeamPostCode(teamDTO.getTeamPostCode());

        if(teamDTO.getUserCode() == foundedTeamPost.getUser().getUserCode()) {
            foundedTeamPost.deleteTeamPost();
            teamPostRepository.save(foundedTeamPost);
        }else{
            throw new CustomException(NOT_MATCH_USERCODE);
        }

    }


    public Date convertStringToDate(String dateString) throws ParseException {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.parse(dateString);
    }



    private TeamPost findByTeamPostCode(Long teamPostCode) {

        Optional<TeamPost> foundTeamPost = teamPostRepository.findById(teamPostCode);
        if(foundTeamPost.isEmpty()){
            log.info("게시글이 지워지거나 존재하지 않습니다.");
            throw new CustomException(NOT_FOUNDED_TEAMPOST);
        }
        return foundTeamPost.get();
    }

}
