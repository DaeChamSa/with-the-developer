package com.developer.comu.command.service;

import com.developer.comu.command.dto.ComuPostCreateDTO;
import com.developer.comu.command.entity.ComuPost;
import com.developer.comu.command.repository.ComuPostRepository;
import com.developer.user.command.entity.User;
import com.developer.user.command.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

}
