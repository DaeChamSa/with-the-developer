package com.developer.comu.command.service;

import com.developer.comu.command.dto.ComuPostDTO;
import com.developer.comu.command.repository.ComuPostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ComuPostService {

    private final ComuPostRepository comuPostRepository;

    public ComuPostService(ComuPostRepository comuPostRepository) {
        this.comuPostRepository = comuPostRepository;
    }


    @Transactional
    public void createComuPost(ComuPostDTO comuPostDTO){
        comuPostRepository.save(comuPostDTO);
    }

}
