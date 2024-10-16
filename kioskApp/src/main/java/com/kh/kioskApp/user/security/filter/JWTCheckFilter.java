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
import com.kh.kioskApp.user.security.exception.JWTExceptions;
import com.kh.kioskApp.user.security.exception.JWTTaskException;
import com.kh.kioskApp.user.security.util.JWTUtil;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
@RequiredArgsConstructor
public class JWTCheckFilter extends OncePerRequestFilter {
	
	private final JWTUtil jwtUtil;

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		if(request.getServletPath().startsWith("/api/v1/token/")) {
			return true;
		}
		
		String path = request.getRequestURI();
		if(!path.startsWith("/api/")) {
			return true;
		}
		
		return false; 
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		log.info("JWT Check Filter ..."); 
		log.info("requestURI: " + request.getRequestURI()); 
		
		String headerStr = request.getHeader("Authorization");
		log.info("headerStr: " + headerStr); 
		
		if(headerStr == null || !headerStr.startsWith("Bearer ")) {
			handleException(response, JWTExceptions.MISSING.get());
			return; 
		}
		
		String accessToken = headerStr.substring(7);
		log.info("Extracted Access Token: " + accessToken);
		try {
			Map<String, Object> tokenMap = jwtUtil.validateToken(accessToken);
			log.info("tokenMap: " + tokenMap);
			
			String email = tokenMap.get("email").toString();
			String[] roles = tokenMap.get("role").toString().split(",");
			
			UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
					new CustomUserPrincipal(email), 
					null, 
					Arrays.stream(roles).map(role -> new SimpleGrantedAuthority("ROLE_" + role)).collect(Collectors.toList())
					);
			
			SecurityContext context = SecurityContextHolder.getContext();
			context.setAuthentication(authenticationToken);
			
			filterChain.doFilter(request, response);
		} catch (ExpiredJwtException e) {
			handleException(response, JWTExceptions.EXPIRED.get());
		} catch (Exception e) {
			handleException(response, JWTExceptions.INVALID.get());
		}
	}
	
	private void handleException(HttpServletResponse response, JWTTaskException e) throws IOException {
		response.setStatus(e.getCode());
		response.setContentType("application/json");
		response.getWriter().println("{\"error\":\"" + e.getMsg() + "\"}");
	}
}
