package com.kh.kioskApp.MenuCategory.exception;

public enum MenuCategoryExceptions {
	MENU_CATEGORY_NOT_REGISTERED("Menu Category Not Registered", 400),
	MENU_CATEGORY_NOT_MATCHED("Menu Category Not Matched", 404),
	MENU_CATEGORY_NOT_FOUND("Menu Category Not Found", 404),
	MENU_CATEGORY_DUPLICATED("Menu Category Duplicated", 409),
	MENU_CATEGORY_NOT_MODIFIED("Menu Category Not Modified", 400),
	MENU_CATEGORY_NOT_REMOVE("Menu Category Not Removed", 400),
	MENU_CATEGORY_MISMATCH("Menu Category Mismatch", 400);
	
	private final MenuCategoryTaskException menuCategoryTaskException;
	
	MenuCategoryExceptions(String msg, int code) {
		menuCategoryTaskException = new MenuCategoryTaskException(msg, code);
	}
	
	public MenuCategoryTaskException get() {
		return menuCategoryTaskException;
	}
}
