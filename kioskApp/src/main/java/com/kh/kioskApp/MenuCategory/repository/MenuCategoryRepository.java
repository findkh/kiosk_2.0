package com.kh.kioskApp.MenuCategory.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kh.kioskApp.MenuCategory.entity.MenuCategoryEntity;

public interface MenuCategoryRepository extends JpaRepository<MenuCategoryEntity, Long>{
	Optional<MenuCategoryEntity> findByCategoryName(String categoryName);
}
