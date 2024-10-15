package com.kh.kioskApp.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.kh.kioskApp.user.security.filter.JWTCheckFilter;

import lombok.extern.log4j.Log4j2;

// Spring Security 관련 설정을 위한 구성 클래스
@Configuration
@Log4j2
public class CustomSecurityConfig {
	
	private JWTCheckFilter jwtCheckFilter; // JWT 체크 필터

	// JWTCheckFilter 의존성 주입
	@Autowired
	private void setJwtCheckFilter(JWTCheckFilter jwtCheckFilter) {
		this.jwtCheckFilter = jwtCheckFilter;
	}
	
	// CORS 설정을 위한 Bean 정의
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration corsConfiguration = new CorsConfiguration();
		
		// 모든 출처에서의 요청을 허용
		corsConfiguration.setAllowedOriginPatterns(List.of("*"));
		// 허용할 HTTP 메소드 정의
		corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "HEAD", "OPTIONS"));
		// 허용할 HTTP 헤더 정의
		corsConfiguration.setAllowedHeaders(List.of("Authorization", "Cache-Control", "Content-Type"));
		// 자격 증명 전송 허용
		corsConfiguration.setAllowCredentials(true);
		// CORS 설정을 위한 소스 등록
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", corsConfiguration);
		return source;
	}
	
	// Spring Security 필터 체인을 설정하는 Bean 정의
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
		log.info("filter chain!");
		
		// 특정 경로에 대한 접근 권한 설정
		httpSecurity.authorizeHttpRequests(authorize -> {
			authorize
				.requestMatchers("/h2-console/**").permitAll() // H2 Console 접근 허용
				.requestMatchers("/swagger-ui/**").permitAll() // Swagger UI 접근 허용
				.requestMatchers("/swagger-resources/**").permitAll()
				.requestMatchers("/v3/api-docs/**").permitAll()
				.requestMatchers("/api/v1/token/**").permitAll() // Token 관련 경로 접근 허용
				.requestMatchers("/api/v1/menuCategory/**").hasRole("ADMIN")
				.anyRequest().authenticated(); // 그 외의 모든 요청은 인증 필요
		});
		
		// H2 Console 접근을 위한 추가 설정
		httpSecurity.headers((headerConfig) -> headerConfig.frameOptions((frameOptionsConfig -> frameOptionsConfig.sameOrigin())));
		
		// 기본 로그인 폼 비활성화
		httpSecurity.formLogin(httpSecurityFormLoginConfigurer -> {
			httpSecurityFormLoginConfigurer.disable();
		});
		
		// 로그아웃 기능 비활성화
		httpSecurity.logout(config -> config.disable());
		
		// CSRF 보호 비활성화
		httpSecurity.csrf(config -> {config.disable();});
		
		// 세션 관리 정책 설정
		httpSecurity.sessionManagement(SessionManagementConfigurer -> {
			SessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.NEVER);
		});
		
		// JWT 체크 필터를 UsernamePasswordAuthenticationFilter 이전에 추가
		httpSecurity.addFilterBefore(jwtCheckFilter, UsernamePasswordAuthenticationFilter.class);
		
		// CORS 설정 적용
		httpSecurity.cors(cors -> {
			cors.configurationSource(corsConfigurationSource());
		});
		
		// 구성한 필터 체인 반환
		return httpSecurity.build();
	}
	
	// 비밀번호 인코더 Bean 정의 (BCrypt 방식)
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
