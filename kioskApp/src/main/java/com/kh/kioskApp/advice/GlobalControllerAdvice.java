package com.kh.kioskApp.advice;

import java.nio.file.AccessDeniedException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.kh.kioskApp.MenuCategory.exception.MenuCategoryTaskException;
import com.kh.kioskApp.menu.exception.MenuTaskException;
import com.kh.kioskApp.user.exception.UserTaskException;
import com.kh.kioskApp.user.security.exception.JWTTaskException;

import lombok.extern.log4j.Log4j2;

@RestControllerAdvice
@Log4j2
public class GlobalControllerAdvice {
	
	@ExceptionHandler(JWTTaskException.class)
	public ResponseEntity<Map<String, String>> handleJWTTaskException(JWTTaskException ex) {
		log.error(ex.getMsg());
		return handleException(ex.getMsg(), ex.getCode());
	}
	
	@ExceptionHandler(UserTaskException.class)
	public ResponseEntity<Map<String, String>> handleUserTaskException(UserTaskException ex) {
		log.error(ex.getMsg());
		String msg = ex.getMsg();
		if(ex.getCode() == 401) {
			msg = "아이디 또는 비밀번호가 올바르지 않습니다.";
		}
		return handleException(msg, ex.getCode());
	}
	
	@ExceptionHandler(MenuCategoryTaskException.class)
	public ResponseEntity<Map<String, String>> handleMenuCategoryTaskException(MenuCategoryTaskException ex) {
		log.error(ex.getMsg());
		return handleException(ex.getMsg(), ex.getCode());
	}
	
	@ExceptionHandler(MenuTaskException.class)
	public ResponseEntity<Map<String, String>> handleMenuTaskException(MenuTaskException ex) {
		log.error(ex.getMsg());
		return handleException(ex.getMsg(), ex.getCode());
	}
	
	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<?> handleAccessDeniedException(AccessDeniedException exception) {
		log.error("handle access denied exception...");
		Map<String, Object> errors = new HashMap<>();
		errors.put("message", exception.getMessage());
		return new ResponseEntity<>(errors, HttpStatus.FORBIDDEN);
	}
	
	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<Map<String, String>> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
		log.error("Data Integrity Violation Exception: " + ex.getMessage());
		return handleException("Duplicated", HttpStatus.CONFLICT.value());
	}
	
	private ResponseEntity<Map<String, String>> handleException(String msg, int status){
		Map<String, String> response = Map.of("error", msg);
		return ResponseEntity.status(status).body(response);
	}
}
