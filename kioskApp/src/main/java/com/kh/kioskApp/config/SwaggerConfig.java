package com.kh.kioskApp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityRequirement;

// Swagger API 문서 생성을 위한 구성 클래스
@Configuration
public class SwaggerConfig {

	// OpenAPI 설정을 위한 Bean 정의
	@Bean
	public OpenAPI openAPI() {
		// API 정보 설정
		Info info = new Info()
			.version("v2.0") // API 버전
			.title("Kiosk API") // API 제목
			.description("Kiosk API..."); // API 설명
		
		String jwt = "JWT"; // JWT 인증 방식을 정의
		// JWT 토큰을 헤더에 포함시키기 위한 보안 요구사항 설정
		SecurityRequirement securityRequirement = new SecurityRequirement().addList(jwt);
		
		// 보안 구성 요소 설정
		Components components = new Components().addSecuritySchemes(jwt, new SecurityScheme()
			.name(jwt) // 보안 스킴 이름
			.type(SecurityScheme.Type.HTTP) // 보안 스킴 타입
			.scheme("bearer") // 인증 스킴 (Bearer Token)
			.bearerFormat("JWT") // Bearer 토큰 형식
		);
		
		// OpenAPI 객체를 생성하고 정보 및 보안 요구사항 추가
		return new OpenAPI().info(info)
				.addSecurityItem(securityRequirement) // 보안 요구사항 추가
				.components(components); // 구성 요소 추가
	}

}
