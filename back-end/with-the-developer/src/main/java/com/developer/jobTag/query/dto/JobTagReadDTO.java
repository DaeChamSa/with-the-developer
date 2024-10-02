package com.developer.jobTag.query.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class JobTagReadDTO {

    private Long jobTagCode;

    private String jobTagName;
}