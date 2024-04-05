package com.inssa.server.config.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Value("${cors.allow.origins}")
    private String[] corsAllowOrigins;

    @Override
    public void addCorsMappings(CorsRegistry registry) {

        final long MAX_AGE_SECS = 3600;

        registry.addMapping("/**") // 모든 경로에 대해
                .allowedOrigins(corsAllowOrigins)
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(MAX_AGE_SECS);
    }
}

