package com.kh.kioskApp.user.exception;

// 사용자 관련 예외를 정의하는 열거형
public enum UserExceptions {
	NOT_FOUND("NOT_FOUND", 404), // 리소스가 발견되지 않았을 때
	DUPLICATE("DUPLICATE", 409), // 중복된 항목이 존재할 때
	INVALID("INVALID", 400), // 잘못된 요청일 때
	BAD_CREDENTIALS("BAD_CREDENTIALS", 401); // 잘못된 자격 증명일 때
	
	private UserTaskException userTaskException; // UserTaskException 인스턴스
	
	// 예외 메시지와 상태 코드를 사용하여 UserTaskException을 초기화
	UserExceptions(String msg, int code) {
		userTaskException = new UserTaskException(msg, code);
	}
	
	// UserTaskException 인스턴스를 반환하는 메소드
	public UserTaskException get() {
		return userTaskException;
	}
}
