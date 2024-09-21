package com.developer.comu.command.service;

import com.developer.common.exception.CustomException;
import com.developer.common.exception.ErrorCode;
import com.developer.comu.command.dto.ComuPostCreateDTO;
import com.developer.comu.command.dto.ComuPostUpdateDTO;
import com.developer.comu.command.entity.ComuPost;
import com.developer.comu.command.repository.ComuPostRepository;
import com.developer.user.command.entity.User;
import com.developer.user.command.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.AccessDeniedException;

@Service
@RequiredArgsConstructor
public class ComuPostService {

    private final ComuPostRepository comuPostRepository;
    private final UserRepository userRepository;

    // 커뮤니티 게시글 등록
    @Transactional
    public Long createComuPost(ComuPostCreateDTO comuPostCreateDTO, String userId){

        User user = userRepository.findByUserId(userId)
                .orElseThrow(RuntimeException::new);

        ComuPost post = new ComuPost(
                user,
                comuPostCreateDTO.getComuSubject(),
                comuPostCreateDTO.getComuContent());

        ComuPost savedComuPost = comuPostRepository.save(post);
        return savedComuPost.getComuCode();
    }

    //커뮤니티 게시글 수정
    @Transactional
    public Long updateComuPost(ComuPostUpdateDTO comuPostUpdateDTO, String userId) throws NotFoundException {

        // 사용자 정보 조회(로그인 사용자와 동일한지 확인)
        User user = userRepository.findByUserId(userId)
                .orElseThrow(()->new NotFoundException("사용자를 찾을 수 없습니다."));

        // 게시글 코드로 조회(업데이트 게시글 찾기)
        ComuPost comuPost = comuPostRepository.findById(comuPostUpdateDTO.getComuCode())
                .orElseThrow(()->new NotFoundException("해당 게시글을 찾을 수 없습니다."));

        // 게시글 작성자 현재 작성자 동일한지 확인
        if(comuPost.getUser().equals(user)){
            comuPost.updateComuPost(comuPostUpdateDTO.getComuSubject(), comuPost.getComuContent());
        }else{
            throw new CustomException(ErrorCode.NOT_MATCH_ROLE);
        }



        return comuPost.getComuCode();
    }



}
