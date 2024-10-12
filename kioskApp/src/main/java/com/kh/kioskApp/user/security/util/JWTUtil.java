package com.kh.kioskApp.user.security.util;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Map;

import javax.crypto.SecretKey;
import javax.management.RuntimeErrorException;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.log4j.Log4j2;

// JWT 토큰 생성 및 검증을 위한 유틸리티 클래스
@Component
@Log4j2
public class JWTUtil {
	private static String key = "123456789123456789123456789123456789"; // 비밀 키 (HMAC SHA 알고리즘에 사용)
	
	// JWT 토큰을 생성하는 메소드
	public String createToken(Map<String, Object> valueMap, int min) {
		SecretKey key = null; // 비밀 키 변수
		try {
			// 비밀 키 생성 (HMAC SHA 알고리즘)
			key = Keys.hmacShaKeyFor(JWTUtil.key.getBytes("UTF-8"));
		} catch (Exception e) {
			// 비밀 키 생성 중 예외 발생 시 런타임 예외로 변환
			throw new RuntimeException(e.getMessage());
		}
		
		// JWT 토큰 생성 및 반환
		return Jwts.builder().header()
				.add("typ", "JWT") // 토큰 타입 설정
				.add("alg", "HS256") // 사용된 알고리즘 설정
				.and()
				.issuedAt(Date.from(ZonedDateTime.now().toInstant())) // 발급 시간
				.expiration(Date.from(ZonedDateTime.now().plusMinutes(min).toInstant())) // 만료 시간
				.claims(valueMap) // 사용자 정보 추가
				.signWith(key) // 서명
				.compact(); // 최종 토큰 생성
	}
	
	// JWT 토큰을 검증하고 claims를 반환하는 메소드
	public Map<String, Object> validateToken(String token) {
		SecretKey key = null; // 비밀 키 변수
		
		try {
			// 비밀 키 생성 (HMAC SHA 알고리즘)
			key = Keys.hmacShaKeyFor(JWTUtil.key.getBytes("UTF-8"));
		} catch (Exception e) {
			// 비밀 키 생성 중 예외 발생 시 런타임 오류 예외로 변환
			throw new RuntimeErrorException(null, e.getMessage());
		}
		
		// 토큰 검증 및 claims 반환
		Claims claims = Jwts.parser().verifyWith(key)
				.build()
				.parseSignedClaims(token) // 서명된 claims 파싱
				.getPayload(); // 실제 payload 반환
		
		log.info("claims: " + claims); // claims 로그
		return claims; // claims 반환
	}

}
