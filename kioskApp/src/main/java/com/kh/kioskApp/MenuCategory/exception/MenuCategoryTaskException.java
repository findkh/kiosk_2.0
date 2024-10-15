package com.kh.kioskApp.MenuCategory.exception;

import lombok.Getter;

@Getter
public class MenuCategoryTaskException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private String msg; // 예외 메시지
	private int code; // 상태 코드
	
	public MenuCategoryTaskException(String msg, int code) {
		this.msg = msg;
		this.code = code;
	}
}
