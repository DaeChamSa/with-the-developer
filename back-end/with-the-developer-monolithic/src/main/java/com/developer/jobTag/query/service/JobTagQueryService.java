package com.developer.jobTag.query.service;

import com.developer.jobTag.query.dto.JobTagReadDTO;
import com.developer.jobTag.query.mapper.JobTagMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JobTagQueryService {

    private final JobTagMapper jobTagMapper;

    public List<JobTagReadDTO> readJobTagList() {
        List<JobTagReadDTO> jobTagList = jobTagMapper.readJobTagList();
        return jobTagList;
    }
}