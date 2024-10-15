package com.kh.kioskApp.token.advice;

import java.nio.file.AccessDeniedException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.kh.kioskApp.user.exception.UserTaskException;

import lombok.extern.log4j.Log4j2;

// 컨트롤러에 대한 전역 예외 처리를 제공하는 어드바이스 클래스
@RestControllerAdvice
@Log4j2
public class TokenControllerAdvice {
	
	// UserTaskException이 발생했을 때 처리하는 메소드
	@ExceptionHandler(UserTaskException.class)
	public ResponseEntity<Map<String, String>> handleTaskException(UserTaskException ex) {
		// 오류 메시지를 로깅
		log.error(ex.getMsg());
		
		String msg = ex.getMsg(); // 예외 메시지 가져오기
		int status = ex.getCode(); // 예외 코드 가져오기
		
		// 응답할 오류 메시지를 맵에 저장
		Map<String, String> map = Map.of("error", msg);
		// 지정된 상태 코드와 함께 오류 메시지를 포함한 응답 반환
		return ResponseEntity.status(status).body(map);
	}
	
	// AccessDeniedException이 발생했을 때 처리하는 메소드
	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<?> handleAccessDeniedException(AccessDeniedException exception) {
		// 접근 거부 예외 처리 로깅
		log.info("handle access denied exception...");
		
		// 오류 메시지를 포함할 맵 생성
		Map<String, Object> errors = new HashMap<>();
		errors.put("message", exception.getMessage()); // 예외 메시지 추가
		
		// FORBIDDEN 상태 코드와 함께 오류 메시지를 포함한 응답 반환
		return new ResponseEntity<>(errors, HttpStatus.FORBIDDEN);
	}
}
