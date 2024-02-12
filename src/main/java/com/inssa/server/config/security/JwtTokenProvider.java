package com.inssa.server.config.security;

import com.inssa.server.api.user.model.AuthUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class JwtTokenProvider {

    private final String AUTHORITIES_KEY = "auth";
    private SecretKey secretKey;

    @PostConstruct
    public void init() {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }
    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.token-validity-in-seconds}")
    private String tokenValidityInSeconds;

    public String createToken(Authentication authentication, String email) {
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        long now = (new Date()).getTime();
        Date validity = new Date(now + Long.parseLong(tokenValidityInSeconds) * 1000); // 토큰 만료 시간

        return Jwts.builder()
                .setHeaderParam(Header.TYPE, "JWT")
                .setSubject(authentication.getName()) // JWT payload 에 저장되는 정보단위
                .claim(AUTHORITIES_KEY, authorities) // 정보 저장
                .claim("email", email)
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .setExpiration(validity) // 만료 시간 세팅
                .compact();
    }

    // 토큰에 담긴 정보로 Authentication 객체 반환
    public Authentication getAuthentication(String token) {
        // 토큰 정보 해석
        Claims claims = Jwts
                .parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();

        Collection<? extends GrantedAuthority> authorities = Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        AuthUser principal = new AuthUser(Long.parseLong(claims.getSubject()), (String)claims.get("email"), "", new ArrayList<>(authorities));

        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    // Request의 Header에서 token 값을 가져오기
    public String getToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if(authorizationHeader == null) {
            return null;
        }

        return authorizationHeader.replaceFirst("Bearer ", "").trim();
    }

    // 유효한 토큰인지 확인
    public boolean validateToken(String jwtToken) {

        try {
            Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(jwtToken);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }
}
