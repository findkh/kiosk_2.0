package com.kh.kioskApp.MenuCategory.dto;

import java.time.LocalDateTime;

import com.kh.kioskApp.MenuCategory.entity.MenuCategoryEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class MenuCategoryDTO {
	private Long no;
	
	private String categoryName;
	
	private LocalDateTime createdDate;
	
	private LocalDateTime modifiedDate;
	
	public MenuCategoryDTO(MenuCategoryEntity entity) {
		this.no = entity.getNo();
		this.categoryName = entity.getCategoryName();
		this.createdDate = entity.getCreatedDate();
		this.modifiedDate = entity.getModifiedDate();
	}
	
	public MenuCategoryEntity toEntity() {
		return MenuCategoryEntity.builder()
				.no(no)
				.categoryName(categoryName)
				.createdDate(createdDate)
				.modifiedDate(modifiedDate)
				.build();
	}
}
