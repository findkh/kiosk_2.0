package com.kh.kioskApp.menu.exception;

public enum MenuExceptions {
	MENU_NOT_REGISTERED("Menu Not Registered", 400),
	MENU_NOT_FOUND("Menu Not Found", 404),
	MENU_NAME_DUPLICATED("Menu Name Duplicated", 409),
	MENU_NOT_MODIFIED("Menu Not Modified", 400),
	MENU_NOT_REMOVE("Menu Not Remove", 400);
	
	private final MenuTaskException menuTaskException;
	
	MenuExceptions(String msg, int code) {
		menuTaskException = new MenuTaskException(msg, code);
	}
	
	public MenuTaskException get() {
		return menuTaskException;
	}
}
