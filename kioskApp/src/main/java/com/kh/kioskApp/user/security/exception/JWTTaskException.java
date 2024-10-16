package com.kh.kioskApp.user.security.exception;

import lombok.Getter;

@Getter
public class JWTTaskException extends RuntimeException  {
	private static final long serialVersionUID = 1L; // 직렬화에 사용할 고유 ID
	private String msg; // 예외 메시지
	private int code; // 상태 코드
	
	// 예외 메시지와 상태 코드를 사용하여 JWTTaskException 초기화
	public JWTTaskException(String msg, int code) {
		this.msg = msg;
		this.code = code;
	}
}