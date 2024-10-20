package com.developer.prefix.query.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

// 사용자에게 보여주는 수식어
@Data
@AllArgsConstructor
public class ResponsePrefixDTO {

    private String dbtiValue;
    private String jobTagName;
}
