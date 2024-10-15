package com.kh.kioskApp.MenuCategory.dto;

import lombok.Data;

@Data
public class UpdateMenuCategoryDTO {
	private String categoryName; // 생성할 카테고리 이름
	private String currentCategoryName; // 수정할 카테고리의 현재 이름
}