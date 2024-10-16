package com.kh.kioskApp.menu.controller.advice;

import java.nio.file.AccessDeniedException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.kh.kioskApp.menu.exception.MenuTaskException;

import lombok.extern.log4j.Log4j2;

@RestControllerAdvice
@Log4j2
public class MenuControllerAdvice {
	
	@ExceptionHandler(MenuTaskException.class)
	public ResponseEntity<Map<String, String>> handleTaskException(MenuTaskException ex) {
		log.error(ex.getMsg());
		
		String msg = ex.getMsg();
		int status = ex.getCode();
		
		Map<String, String> map = Map.of("error", msg);
		return ResponseEntity.status(status).body(map);
	}
	
	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<?> handleAccessDeniedException(AccessDeniedException exception) {
		log.error("handle access denied exception");
		
		Map<String, Object> errors = new HashMap<>();
		errors.put("message", exception.getMessage());
		
		return new ResponseEntity<>(errors, HttpStatus.FORBIDDEN);
		
	}
	
	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<Map<String, String>> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
		log.error("Data Integrity Violation Exception: " + ex.getMessage());
		
		Map<String, String> response = new HashMap<>();
		response.put("error", "Duplicated");
		
		return ResponseEntity
			.status(HttpStatus.CONFLICT)
			.body(response);
	}


}
