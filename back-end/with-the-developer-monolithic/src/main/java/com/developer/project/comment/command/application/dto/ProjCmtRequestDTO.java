package com.developer.project.comment.command.application.dto;

import com.developer.project.comment.command.domain.aggregate.ProjCmt;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProjCmtRequestDTO {

    private String projCmtContent;

    public ProjCmt toEntity() {
        return ProjCmt.builder()
                .projCmtContent(projCmtContent).build();
    }
}
