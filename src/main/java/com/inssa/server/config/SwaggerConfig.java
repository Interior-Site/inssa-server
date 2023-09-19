package com.inssa.server.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

	/**
	 * Basic Swagger Description
	 */
	private static final String TITLE = "Inssa API";
	private static final String DESCRIPTION = "Inssa API 명세서";
	private static final String VERSION = "v1.0.0.";

	private static final String AUTH_NAME = "jwtAuth";


	private Info getInfo() {
		return new Info()
				.title(TITLE)
				.description(DESCRIPTION)
				.version(VERSION);
	}

	private SecurityScheme getSecurityScheme() {
		return new SecurityScheme()
				.name("Authorization")
				.in(SecurityScheme.In.HEADER)
				.type(SecurityScheme.Type.HTTP)
				.scheme("bearer")
				.bearerFormat("{{ Bearer }}");
	}


	@Bean
	public OpenAPI openAPI() {
		Info info = getInfo();

		// API 요청헤더에 인증정보 포함
		Components components = new Components()
				.addSecuritySchemes(AUTH_NAME, getSecurityScheme());
		SecurityRequirement securityItem = new SecurityRequirement().addList(AUTH_NAME);

		return new OpenAPI()
				.info(info)
				.components(components)
				.addSecurityItem(securityItem);
	}


	/**
	 * API Groups
	 */

	@Bean
	public GroupedOpenApi allGroup() {
		return GroupedOpenApi.builder()
				.group("All - 전체 보기")
				.packagesToScan("com.inssa.server.api")
				.build();
	}

	@Bean
	public GroupedOpenApi boardGroup() {
		return GroupedOpenApi.builder()
				.group("Board - 게시판")
			 	.packagesToScan("com.inssa.server.api.board")
				.build();
	}

	@Bean
	public GroupedOpenApi bookmarkGroup() {
		return GroupedOpenApi.builder()
				.group("Bookmark - 찜")
				.packagesToScan("com.inssa.server.api.board.bookmark")
				.build();
	}

	@Bean
	public GroupedOpenApi companyGroup() {
		return GroupedOpenApi.builder()
				.group("Company - 기업")
				.packagesToScan("com.inssa.server.api.company")
				.build();
	}

	@Bean
	public GroupedOpenApi imageGroup() {
		return GroupedOpenApi.builder()
				.group("Image - 이미지")
				.packagesToScan("com.inssa.server.api.image")
				.build();
	}

	@Bean
	public GroupedOpenApi userGroup() {
		return GroupedOpenApi.builder()
				.group("User - 사용자")
				.packagesToScan("com.inssa.server.api.user")
				.build();
	}


}
