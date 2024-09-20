package com.developer.comu.command.service;

import com.developer.comu.command.dto.ComuPostDTO;
import com.developer.comu.command.entity.ComuPost;
import com.developer.comu.command.repository.ComuPostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ComuPostService {

    private final ComuPostRepository comuPostRepository;

    // 커뮤니티 게시글 등록
    @Transactional
    public ComuPost createComuPost(ComuPostDTO comuPostDTO){
        ComuPost post = new ComuPost(
                comuPostDTO.getComuSubject(),
                comuPostDTO.getComuContent());

        ComuPost save = comuPostRepository.save(post);

        return save;
    }

}
