package com.developer.search.query.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class SearchIntegratedResultDTO {

    private String title;

    private String content;

    private LocalDateTime createdDate;

    private String source;
}