package com.kh.kioskApp.menu.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kh.kioskApp.menu.entity.MenuEntity;

public interface MenuRepository extends JpaRepository<MenuEntity, Long>{

	List<MenuEntity> findByMenuCategoryNo(Long categoryId);

	boolean existsByMenuName(String menuName);
}
