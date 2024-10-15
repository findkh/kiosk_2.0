package com.kh.kioskApp.user.security.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.kh.kioskApp.user.security.auth.CustomUserPrincipal;
import com.kh.kioskApp.user.security.util.JWTUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

// JWT 토큰을 검증하는 필터 클래스
@Component
@Log4j2
public class JWTCheckFilter extends OncePerRequestFilter {
	
	private JWTUtil jwtUtil; 
	
	public JWTCheckFilter(JWTUtil jwtUtil) {
		this.jwtUtil = jwtUtil;
	}
	
	// 특정 요청 경로에 대해 필터를 적용하지 않도록 설정
	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		
		// /api/v1/token/ 경로는 필터링하지 않음
		if(request.getServletPath().startsWith("/api/v1/token/")) {
			return true;
		}
		
		String path = request.getRequestURI();
		
		// /api/로 시작하지 않는 경로도 필터링하지 않음
		if(!path.startsWith("/api/")) {
			return true;
		}
		
		return false; // 그 외의 경로는 필터링
	}

	// 실제 필터링 로직을 구현하는 메소드
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		log.info("JWT Check Filter ..."); // 필터 실행 로그
		log.info("requestURI: " + request.getRequestURI()); // 요청 URI 로그
		
		// Authorization 헤더에서 JWT 토큰을 가져옴
		String headerStr = request.getHeader("Authorization");
		log.info("headerStr: " + headerStr); // 헤더 값 로그
		
		// Access Token이 없는 경우 예외 처리
		if(headerStr == null || !headerStr.startsWith("Bearer ")) {
			handleException(response, new Exception("ACCESS TOKEN NOT FOUND"));
			return; // 예외 발생 시 처리 후 종료
		}
		
		// Bearer 토큰에서 실제 JWT 부분만 추출
		String accessToken = headerStr.substring(7);
		System.out.println(accessToken);
		try {
			System.out.println("여기");
			// JWT 토큰 검증
			Map<String, Object> tokenMap = jwtUtil.validateToken(accessToken);
			
			System.out.println("?????" + tokenMap);
			
			// 정상 처리인 경우
			log.info("tokenMap: " + tokenMap);
			
			String email = tokenMap.get("email").toString(); // 이메일 정보
			String[] roles = tokenMap.get("role").toString().split(","); // 역할 정보
			
			// 인증 토큰 생성
			UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
					new CustomUserPrincipal(email), // 사용자 주체
					null, // 비밀번호는 필요하지 않으므로 null
					Arrays.stream(roles).map(role -> new SimpleGrantedAuthority("ROLE_" + role)).collect(Collectors.toList()) // 권한 리스트
					);
			
			// SecurityContext에 인증 정보 설정
			SecurityContext context = SecurityContextHolder.getContext();
			context.setAuthentication(authenticationToken);
			
			// 다음 필터로 요청 전달
			filterChain.doFilter(request, response);
		} catch (Exception e) {
			// 예외 발생 시 처리
			handleException(response, e);
		}
	}
	
	// 예외 처리 메소드
	private void handleException(HttpServletResponse response, Exception e) throws IOException {
		response.setStatus(HttpServletResponse.SC_FORBIDDEN); // HTTP 403 상태 코드
		response.setContentType("application/json"); // 응답 형식 설정
		response.getWriter().println("{\"error\":\"" + e.getMessage() + "\"}"); // 오류 메시지 반환
	}

}
