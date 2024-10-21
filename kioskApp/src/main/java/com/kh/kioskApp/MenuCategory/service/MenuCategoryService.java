package com.kh.kioskApp.MenuCategory.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.kh.kioskApp.MenuCategory.entity.MenuCategoryEntity;
import com.kh.kioskApp.MenuCategory.exception.MenuCategoryExceptions;
import com.kh.kioskApp.MenuCategory.repository.MenuCategoryRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MenuCategoryService {

	private final MenuCategoryRepository menuCategoryRepository;

	@Transactional
	public void createCategory(String categoryName) {
		// 중복 체크
		if (menuCategoryRepository.findByCategoryName(categoryName).isPresent()) {
			throw MenuCategoryExceptions.MENU_CATEGORY_DUPLICATED.get();
		}
		
		// 카테고리 생성
		MenuCategoryEntity entity = new MenuCategoryEntity();
		entity.setCategoryName(categoryName);
		menuCategoryRepository.save(entity);
	}
	
	public List<MenuCategoryEntity> getAllCategories() {
		return menuCategoryRepository.findAll();
	}

	@Transactional
	public void updateCategoryName(Long currentNo, String newName) {
		Optional<MenuCategoryEntity> optionalEntity = menuCategoryRepository.findByCategoryNo(currentNo);

		// 카테고리가 존재하는지 확인
		if (!optionalEntity.isPresent()) {
			throw MenuCategoryExceptions.MENU_CATEGORY_NOT_MATCHED.get();
		}

		// 중복 체크
		if (menuCategoryRepository.findByCategoryName(newName).isPresent()) {
			throw MenuCategoryExceptions.MENU_CATEGORY_DUPLICATED.get();
		}

		// 카테고리 수정
		MenuCategoryEntity entity = optionalEntity.get();
		entity.changeCategoryName(newName);
		menuCategoryRepository.save(entity);
	}

}
