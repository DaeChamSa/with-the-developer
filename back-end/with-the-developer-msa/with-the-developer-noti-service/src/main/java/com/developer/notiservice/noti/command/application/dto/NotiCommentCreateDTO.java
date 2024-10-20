package com.developer.notiservice.noti.command.application.dto;

import com.developer.notiservice.noti.command.domain.aggregate.PostType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotiCommentCreateDTO {

    private Long userCode;
    private Long postCode;
    private PostType postType;
}
