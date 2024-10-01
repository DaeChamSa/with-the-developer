package com.developer.bookmark.command.application.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookmarkRegistDTO {

    @NotNull(message = "북마크 주소는 필수로 입력되어야 합니다.")
    private String bmkUrl;
    @NotNull(message = "북마크 제목은 필수로 입력되어야 합니다.")
    private String bmkTitle;

    @NotNull(message = "게시글 타입은 필수로 입력되어야 합니다.")
    private String postType;
    @NotNull(message = "게시글 코드는 필수로 입력되어야 합니다.")
    private Long postCode;

    private Long userCode;
}
