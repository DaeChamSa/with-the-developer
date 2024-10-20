package com.developer.jobTag.query.mapper;

import com.developer.jobTag.query.dto.JobTagReadDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface JobTagMapper {
    List<JobTagReadDTO> readJobTagList();
}