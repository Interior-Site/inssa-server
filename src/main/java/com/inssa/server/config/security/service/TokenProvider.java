package com.inssa.server.config.security.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TokenProvider extends AbstractUserDetailsAuthenticationProvider { // 토큰 생성 및 유효성 검증
    // AbstractUserDetailsAuthenticationProvider > AuthenticationProvider 인터페이스를 구현한 추상 클래스

    private TokenService auth;

    // 커스텀한 유효성 체크 로직을 여기서 추가
    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {}

    // 캐시에 없는 경우 user 객체를 찾아옴
    @Override
    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        String token = authentication.getCredentials().toString();

        log.info("Token Provider => token : {}", token);
        return auth.findAuthUserByToken(token);
    }
}
