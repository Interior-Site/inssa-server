package com.inssa.server.config.security;

import jakarta.servlet.DispatcherType;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@EnableWebSecurity // Spring Security 설정 활성화
@EnableMethodSecurity(securedEnabled = true, prePostEnabled = true)
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;

    @Value("${spring.profiles.active: null}")
    private String activeProfile;

    private static final List<AntPathRequestMatcher> PERMIT_URL_ARRAY = Stream.of(
            /* swagger v2 */
            new AntPathRequestMatcher("v2/api-docs"),
            new AntPathRequestMatcher("swagger-resources"),
            new AntPathRequestMatcher("swagger-resources/**"),
            new AntPathRequestMatcher("configuration/ui"),
            new AntPathRequestMatcher("configuration/security"),
            new AntPathRequestMatcher("swagger-ui.html"),
            new AntPathRequestMatcher("webjars/**"),

            /* swagger v3 */
            new AntPathRequestMatcher("v3/api-docs/**"),
            new AntPathRequestMatcher("swagger-ui/**"),

            new AntPathRequestMatcher("api/v1/**")
    ).collect(Collectors.toList());

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * 스프링 시큐리티 설정
     *
     * @param http
     * @throws Exception
     */
    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // swagger API 호출시 403 에러 발생 방지
                .headers(c -> c.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin))
                .sessionManagement(c -> c.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(
                        request -> request
                                .dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()
                                .requestMatchers(PERMIT_URL_ARRAY.toArray(AntPathRequestMatcher[]::new)).permitAll()
                                .anyRequest().authenticated()
                )
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class); // UsernamePasswordAuthenticationFilter 앞에 커스텀 필터 추가
        return http.build();
    }

}
