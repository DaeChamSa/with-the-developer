package com.developer.dbti.command.domain.aggregate;

import java.util.Arrays;

public enum DbtiRole {


    // 백엔드 형용사
    BACKEND,
    // 프론트엔드 형용사
    FRONTEND,
    // 디자이너 형용사
    DESIGNER,
    // PM 형용사
    PM;

    // 특정 값이 존재하는지 확인하는 메서드
    public static boolean contains(String role) {
        return Arrays.stream(values())
                .anyMatch(dbtiRole -> dbtiRole.name().equalsIgnoreCase(role));
    }

}
