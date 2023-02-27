package com.inssa.server.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

@Configuration public class WebConfig {

    @Value("${spring.resource.path}")private String resourcePath;

    @Value("${spring.upload.path}") private String uploadPath;

    public void addResourceHandler(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(uploadPath).addResourceLocations(resourcePath);
    }
}
