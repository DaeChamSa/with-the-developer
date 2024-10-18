package com.developer.common.module;

import com.developer.jobTag.command.entity.JobTag;
import com.developer.jobTag.command.repository.JobTagRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class JobTagInitializer {

    private final JobTagRepository jobTagRepository;

    @PostConstruct
    public void init(){

        if (jobTagRepository.findAll().isEmpty()) {
            List<JobTag> jobTags = Arrays.asList(
                    new JobTag("데이터 엔지니어"),
                    new JobTag("백엔드"),
                    new JobTag("프론트엔드"),
                    new JobTag("디자이너"),
                    new JobTag("기획자"),
                    new JobTag("퍼블리셔"),
                    new JobTag("PM"),
                    new JobTag("AI 개발자")
            );

            jobTagRepository.saveAll(jobTags);

            log.info("Job Tag 초기 저장 성공 {}", jobTags);
        }
    }
}
