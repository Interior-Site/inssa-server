package com.inssa.server.api.user.annotation;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD) // 메서드 단위로 사용
@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("@authService.isLoggedInAndActive()")
public @interface LoggedInAndActive {
}
