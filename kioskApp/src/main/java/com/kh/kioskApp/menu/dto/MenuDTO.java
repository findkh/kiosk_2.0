package com.kh.kioskApp.menu.dto;

import java.time.LocalDateTime;

import com.kh.kioskApp.MenuCategory.dto.MenuCategoryDTO;
import com.kh.kioskApp.menu.entity.MenuEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class MenuDTO {
	
	private Long id;
	
	private String menuName;
	
	private Integer menuPrice;
	
	private boolean isActive;
	
	private String createdId;
	
	private LocalDateTime createdDate;
	
	private String modifiedId;
	
	private LocalDateTime modifiedDate;
	
	private MenuCategoryDTO menuCategory;
	
	public MenuDTO(MenuEntity entity) {
		this.id = entity.getId();
		this.menuName = entity.getMenuName();
		this.menuPrice = entity.getMenuPrice();
		this.isActive = entity.isActive();
		this.createdId = entity.getCreatedId();
		this.createdDate = entity.getCreatedDate();
		this.modifiedId = entity.getModifiedId();
		this.modifiedDate = entity.getModifiedDate();
		this.menuCategory = new MenuCategoryDTO(entity.getMenuCategory());
	}
	
	public MenuEntity toEntity() {
		return MenuEntity.builder()
				.id(id)
				.menuName(menuName)
				.menuPrice(menuPrice)
				.isActive(isActive)
				.createdId(createdId)
				.createdDate(createdDate)
				.modifiedId(modifiedId)
				.modifiedDate(modifiedDate)
				.menuCategory(menuCategory.toEntity())
				.build();
	}
}
