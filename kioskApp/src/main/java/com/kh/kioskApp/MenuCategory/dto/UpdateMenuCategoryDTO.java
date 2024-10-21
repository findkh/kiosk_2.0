package com.kh.kioskApp.MenuCategory.dto;

import lombok.Data;

@Data
public class UpdateMenuCategoryDTO {
	private String categoryName; // 생성할 카테고리 이름
	private Long no;
}