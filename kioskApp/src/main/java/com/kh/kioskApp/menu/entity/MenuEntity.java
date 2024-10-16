package com.kh.kioskApp.menu.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.kh.kioskApp.MenuCategory.entity.MenuCategoryEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_menu")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class MenuEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "menu_category_id", nullable = false)  // 'menu_category_id'는 DB에서 해당 카테고리 ID를 참조하는 외래키
	private MenuCategoryEntity menuCategory;

	@Column(unique = true)
	private String menuName;
	
	private Integer menuPrice;
	
	private boolean isActive;
	
	private String createdId;

	@CreatedDate
	private LocalDateTime createdDate;

	private String modifiedId;

	@LastModifiedDate
	private LocalDateTime modifiedDate;
	
	public void changeMenuName(String menuName) {
		this.menuName = menuName;
	}
	
	public void changeMenuPrice(Integer menuPrice) {
		this.menuPrice = menuPrice;
	}
	
	public void changeIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
}
