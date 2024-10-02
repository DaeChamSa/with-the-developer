package com.developer.team.post.command.service;

import com.developer.common.exception.CustomException;
import com.developer.common.exception.ErrorCode;
import com.developer.jobTag.command.entity.JobTag;
import com.developer.jobTag.command.entity.TeamTag;
import com.developer.jobTag.command.repository.JobTagRepository;
import com.developer.jobTag.command.repository.TeamTagRepository;
import com.developer.team.post.command.dto.TeamPostDeleteDTO;
import com.developer.team.post.command.dto.TeamPostRegistDTO;
import com.developer.team.post.command.dto.TeamPostUpdateDTO;
import com.developer.team.post.command.entity.TeamPost;
import com.developer.team.post.command.repository.TeamPostRepository;
import com.developer.user.command.domain.aggregate.User;
import com.developer.user.command.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class TeamPostCommandService {

    private final TeamPostRepository teamPostRepository;
    private final UserRepository userRepository;
    private final JobTagRepository jobTagRepository;
    private final TeamTagRepository teamTagRepository;

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

        for(String jobTagName : teamDTO.getJobTagNames()){

            JobTag jobTag = jobTagRepository.findByJobTagName(jobTagName)
                    .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_JOB_TAG));

            TeamTag teamTag = new TeamTag(teamPost, jobTag);

            teamPost.addTeamTag(teamTag);

        }

        // 팀 게시물 저장
        teamPostRepository.save(teamPost);
        // 팀 태그 저장
        teamTagRepository.saveAll(teamPost.getTeamTags());

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

            // 게시글 정보 수정
            foundedTeamPost.updateTeamPost(teamDTO.getTeamPostTitle(), teamDTO.getTeamContent());
            foundedTeamPost.updateDeadline(deadline);

            // 새로운 JobTag 목록으로 새 TeamTag 생성
            List<JobTag> newJobTags = jobTagRepository.findAllByJobTagNameIn(teamDTO.getJobTagNames());
            List<TeamTag> newTeamTags = newJobTags.stream()
                    .map(jobTag -> new TeamTag(foundedTeamPost, jobTag))
                    .collect(Collectors.toList());

            // 삭제 후 TeamPost에서 기존 태그 참조 제거
            foundedTeamPost.getTeamTags().clear();

            // 새로운 TeamTag 추가
            foundedTeamPost.getTeamTags().addAll(newTeamTags);

            // 게시글 저장
            teamPostRepository.save(foundedTeamPost);

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
            // 팀 모집 게시글 직무 태그 삭제
            teamTagRepository.deleteAll(foundedTeamPost.getTeamTags());
            // 팀 모집 게시글 삭제
            teamPostRepository.delete(foundedTeamPost);
        }else{
            throw new CustomException(ErrorCode.UNAUTHORIZED_USER);
        }
    }

    // 날짜 데이터 파싱
    public Date convertStringToDate(String dateString) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
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
