package com.developer.noti.command.application.dto;

import com.developer.noti.command.domain.aggregate.PostType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class NotiCommentCreateDTO {

    private Long userCode;
    private Long postCode;
    private PostType postType;
}
