package com.developer.noti.command.application.dto;

import com.developer.noti.command.domain.aggregate.PostType;
import lombok.Data;

@Data
public class NotiPostCreateDTO {

    private Long userCode;
    private Long postCode;
    private PostType postType;
}
