package com.developer.teampost.command.service;

import com.developer.common.exception.CustomException;
import com.developer.common.exception.ErrorCode;
import com.developer.teampost.command.dto.TeamPostDeleteDTO;
import com.developer.teampost.command.dto.TeamPostRegistDTO;
import com.developer.teampost.command.dto.TeamPostUpdateDTO;
import com.developer.teampost.command.entity.TeamPost;
import com.developer.teampost.command.repository.TeamPostRepository;
import com.developer.user.command.entity.User;
import com.developer.user.command.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TeamPostCommandService {

    private final TeamPostRepository teamPostRepository;
    private final UserRepository userRepository;

    // 팀 모집 게시글 작성
    @Transactional
    public Long registTeamPost(TeamPostRegistDTO teamDTO) throws ParseException {

        // 연관관계 매핑을 위한 현재 로그인 중인 유저 조회(엔티티 생성)
        User user = userRepository.findById(teamDTO.getUserCode())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));

        // 문자열로 들어온 날짜 데이터 파싱
        Date deadline = convertStringToDate(teamDTO.getTeamPostDeadLine());

        // 팀모집 게시글 엔터티 생성
        TeamPost teamPost = teamDTO.toEntity();
        teamPost.updateDeadline(deadline);
        teamPost.updateUser(user);

        teamPostRepository.save(teamPost);

        // 생성 된 teamPost 의 고유 번호 반환
        return teamPost.getTeamPostCode();
    }

    @Transactional
    public void updateTeamPost(TeamPostUpdateDTO teamDTO) throws ParseException {

        Date deadline = convertStringToDate(teamDTO.getTeamPostDeadLine());

        // 수정할 게시글 조회
        TeamPost foundedTeamPost = findByTeamPostCode(teamDTO.getTeamPostCode());

        // 수정하려는 게시글이 현재 로그인 중인 유저의 게시글인지 확인
        if(teamDTO.getUserCode().equals(foundedTeamPost.getUser().getUserCode())) {
            // 맞다면 해당 팀 모집 게시판 엔터티 수정 후 저장
            foundedTeamPost.updateTeamPost(teamDTO.getTeamPostTitle(), teamDTO.getTeamContent());
            foundedTeamPost.updateDeadline(deadline);
        }else{
            throw new CustomException(ErrorCode.UNAUTHORIZED_USER);
        }
    }

    @Transactional
    public void deleteTeamPost(TeamPostDeleteDTO teamDTO) throws ParseException {

        // 삭제 할 게시글 조회
        TeamPost foundedTeamPost = findByTeamPostCode(teamDTO.getTeamPostCode());

        // 삭제하려는 게시글이 현재 로그인 중인 유저의 게시글인지 확인
        if(teamDTO.getUserCode().equals(foundedTeamPost.getUser().getUserCode())) {
            teamPostRepository.delete(foundedTeamPost);
        }else{
            throw new CustomException(ErrorCode.UNAUTHORIZED_USER);
        }
    }

    // 날짜 데이터 파싱
    public Date convertStringToDate(String dateString) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.parse(dateString);
    }

    // 팀 게시글 고유 번호로 게시글 조회
    private TeamPost findByTeamPostCode(Long teamPostCode) {
        Optional<TeamPost> foundTeamPost = teamPostRepository.findById(teamPostCode);

        if(foundTeamPost.isEmpty()){
            log.info("게시글이 지워지거나 존재하지 않습니다.");
            throw new CustomException(ErrorCode.NOT_FOUND_POST);
        }

        return foundTeamPost.get();
    }
}
