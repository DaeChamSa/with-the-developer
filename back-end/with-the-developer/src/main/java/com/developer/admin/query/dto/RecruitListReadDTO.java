package com.developer.admin.query.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class RecruitListReadDTO {

    private String recruitTitle;

    public LocalDateTime recruitPostDate;

    private boolean recruitApprStatus;
}
