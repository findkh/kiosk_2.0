package com.kh.kioskApp.menu.exception;

import lombok.Getter;

@Getter
public class MenuTaskException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private String msg;
	private int code;
	
	public MenuTaskException(String msg, int code) {
		this.msg = msg;
		this.code = code;
	}
} 
