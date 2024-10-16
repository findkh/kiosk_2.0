package com.kh.kioskApp.user.security.exception;

public enum JWTExceptions {
	EXPIRED("EXPIRED", 401), // JWT가 만료된 경우
	INVALID("INVALID", 400), // 잘못된 JWT인 경우
	MISSING("MISSING", 403), // JWT가 없는 경우
	NO_ACCESS_TOKEN("No Access token", 400),
	NO_REFRESH_TOKEN("No Refresh Token", 400),
	NO_EMAIL("No Emial", 400);
	
	private JWTTaskException jwtTaskException; // JWTTaskException 인스턴스
	
	JWTExceptions(String msg, int code) {
		jwtTaskException = new JWTTaskException(msg, code);
	}
	
	public JWTTaskException get() {
		return jwtTaskException;
	}
}