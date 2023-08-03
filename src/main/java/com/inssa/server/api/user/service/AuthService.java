package com.inssa.server.api.user.service;

import com.inssa.server.api.user.model.AuthUser;
import com.inssa.server.common.code.ErrorCode;
import com.inssa.server.common.exception.InssaException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collection;

/**
 * 사용자 인증, 인가 관련 서비스
 */
@RequiredArgsConstructor
@Service
public class AuthService {

    /**
     * 로그인 및 활성 여부 검사
     * @return 사용자 로그인 및 활성화 시 true, 아닐 시 관련 예외 발생
     */
    public boolean isLoggedInAndActive() {
        // 사용자 인증 정보
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // 로그인 여부 검사
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            // 토큰 헤더 유효성 검사
            if (principal instanceof AuthUser authuser) {
                Collection<? extends GrantedAuthority> authorities = authuser.getAuthorities();
                // TODO: 활성 여부 검사 로직
                if (!CollectionUtils.isEmpty(authorities)) {
                    return true;
                } else {
                    throw new InssaException(ErrorCode.U000_INVALID_HEADER);
                }
            } else {
                throw new InssaException(ErrorCode.U000_INVALID_HEADER);
            }
        } else {
            throw new InssaException(ErrorCode.U001_UNAUTHENTICATED);
        }
    }
}
