package com.developer.postservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotiCommentCreateDTO {

    private Long userCode;
    private Long postCode;
    private String postType;
}
