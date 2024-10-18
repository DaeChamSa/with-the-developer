package com.developer.team.post.query.dto;

import com.developer.image.command.entity.Image;
import lombok.Data;

import java.util.List;

// 팀 모집 게시글 상세 정보
@Data
public class TeamPostDTO {

    private Long teamPostCode;

    private String teamPostTitle;

    private String teamContent;

    private String createdDate;

    private String modifiedDate;

    private String teamDeadline;

    private Long userCode;

    private String userNick;

    private List<String> jobTagNames;

    private List<Image> images;

}
