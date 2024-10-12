package com.kh.kioskApp.user.exception;

import lombok.Getter;

// 사용자 작업 관련 예외를 정의하는 클래스
@Getter
public class UserTaskException extends RuntimeException  {
	private static final long serialVersionUID = 1L; // 직렬화에 사용할 고유 ID
	private String msg; // 예외 메시지
	private int code; // 상태 코드
	
	// 예외 메시지와 상태 코드를 사용하여 UserTaskException 초기화
	public UserTaskException(String msg, int code) {
		this.msg = msg;
		this.code = code;
	}
}
