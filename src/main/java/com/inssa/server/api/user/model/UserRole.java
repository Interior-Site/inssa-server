package com.inssa.server.api.user.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserRole {
    ANONYMOUS("ROLE_ANONYMOUS", "손님"),
    USER("ROLE_USER", "일반 사용자"),
    EMPLOYEE("ROLE_COMPANY", "업체 사용자"),
    ADMIN("ROLE_ADMIN", "시스템 관리자"),
    SOCIAL("ROLE_SOCIAL", "소셜 사용자");

    private final String key;
    private final String title;
}
