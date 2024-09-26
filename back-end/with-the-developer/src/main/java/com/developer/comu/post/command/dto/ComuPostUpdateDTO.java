package com.developer.comu.post.command.dto;

import com.developer.comu.command.entity.ComuPost;
import lombok.Data;

@Data
public class ComuPostUpdateDTO {

    private Long comuCode;
    private String comuSubject;
    private String comuContent;
}
