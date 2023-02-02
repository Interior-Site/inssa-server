package com.inssa.server.config.security.web;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.http.HttpHeaders;

public class JwtTokenBaseAuthenticationFilter extends GenericFilterBean { // JWT를 위한 커스텀 필터

    private AuthenticationProvider tokenProvider; // 화면에서 입력한 로그인 정보와 DB에서 가져온 사용자의 정보를 비교해주는 인터페이스

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

    }
}
