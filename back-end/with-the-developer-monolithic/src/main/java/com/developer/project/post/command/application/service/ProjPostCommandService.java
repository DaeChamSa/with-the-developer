package com.developer.project.post.command.application.service;

import com.developer.common.exception.CustomException;
import com.developer.common.exception.ErrorCode;
import com.developer.project.post.command.application.dto.ProjPostRequestDTO;
import com.developer.project.post.command.domain.aggregate.ProjPost;
import com.developer.project.post.command.domain.aggregate.ProjTag;
import com.developer.project.post.command.domain.repository.ProjPostRepository;
import com.developer.project.post.command.domain.repository.ProjTagRepository;
import com.developer.user.command.domain.aggregate.User;
import com.developer.user.command.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ProjPostCommandService {

    private final ProjPostRepository projPostRepository;
    private final UserRepository userRepository;
    private final ProjTagRepository projTagRepository;

    @Transactional
    public Long createProjPost(Long loginUserCode, ProjPostRequestDTO projPostRequestDTO) {
        User user = userRepository.findById(loginUserCode)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));
        ProjPost projPost = projPostRequestDTO.toEntity();
        projPost.updateUser(user.getUserCode());
        ProjPost savedProjPost = projPostRepository.save(projPost);

        List<ProjTag> newProjTags = new ArrayList<>();

        for(String content: projPostRequestDTO.getProjTagContent()){
            newProjTags.add(new ProjTag(content,projPost));
        }

        if(!newProjTags.isEmpty()){
            projTagRepository.saveAll(newProjTags);
        }

        savedProjPost.addProjTag(newProjTags);

        return savedProjPost.getProjPostCode();
    }

    @Transactional
    public void updateProjPost(Long projPostCode, Long loginUserCode, ProjPostRequestDTO projPostRequestDTO) {
        ProjPost foundPost = projPostRepository.findById(projPostCode)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_POST));
        List<ProjTag> oldProjTags = foundPost.getProjTags();
        List<ProjTag> newProjTags = new ArrayList<>();

        for(String content: projPostRequestDTO.getProjTagContent()){
            newProjTags.add(new ProjTag(content,foundPost));
        }

        if (foundPost.getUserCode().equals(loginUserCode)) {
            // 기존 태그 삭제 후 새 태그 저장
            projTagRepository.deleteAll(oldProjTags);
            projTagRepository.saveAll(newProjTags);

            foundPost.updateProjPost(projPostRequestDTO.getProjPostTitle(), projPostRequestDTO.getProjPostContent(), projPostRequestDTO.getProjUrl());
        } else {
            throw new CustomException(ErrorCode.UNAUTHORIZED_USER);
        }
    }

    @Transactional
    public void deleteProjPost(Long loginUserCode, Long projPostCode) {
        ProjPost foundPost = projPostRepository.findById(projPostCode)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_POST));
        List<ProjTag> projTags = foundPost.getProjTags();

        if (foundPost.getUserCode().equals(loginUserCode)) {
            projTagRepository.deleteAll(projTags);
            projPostRepository.deleteById(projPostCode);
        } else {
            throw new CustomException(ErrorCode.UNAUTHORIZED_USER);
        }
    }
}
