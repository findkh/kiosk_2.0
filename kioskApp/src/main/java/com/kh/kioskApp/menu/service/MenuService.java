package com.kh.kioskApp.menu.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.kioskApp.MenuCategory.entity.MenuCategoryEntity;
import com.kh.kioskApp.MenuCategory.exception.MenuCategoryExceptions;
import com.kh.kioskApp.MenuCategory.repository.MenuCategoryRepository;
import com.kh.kioskApp.menu.dto.MenuDTO;
import com.kh.kioskApp.menu.entity.MenuEntity;
import com.kh.kioskApp.menu.exception.MenuExceptions;
import com.kh.kioskApp.menu.repository.MenuRepository;

import jakarta.transaction.Transactional;

@Service
public class MenuService {

	@Autowired
	private MenuRepository menuRepository;
	
	@Autowired
	private MenuCategoryRepository menuCategoryRepository;

	/**
	 * 모든 메뉴를 조회
	 * 
	 * @return List<MenuDTO> 조회된 모든 메뉴 정보를 DTO로 변환한 리스트
	 * @throws MenuExceptions 메뉴가 존재하지 않을 경우 발생하는 예외
	 */
	public List<MenuDTO> getAllMenus() {
		List<MenuEntity> menus = menuRepository.findAll();
		if (menus.isEmpty()) {
			throw MenuExceptions.MENU_NOT_FOUND.get();
		}
		return menus.stream().map(MenuDTO::new).collect(Collectors.toList());
	}

	/**
	 * 특정 카테고리의 메뉴를 조회
	 * 
	 * @param categoryId 카테고리 번호(Long)
	 * @return List<MenuDTO> 해당 카테고리의 메뉴 정보를 DTO로 변환한 리스트
	 * @throws MenuExceptions 카테고리에 해당하는 메뉴가 없을 경우 발생하는 예외
	 */
	public List<MenuDTO> getMenusByCategoryId(Long categoryId) {
		List<MenuEntity> menus = menuRepository.findByMenuCategoryNo(categoryId);
		if (menus.isEmpty()) {
			throw MenuExceptions.MENU_NOT_FOUND.get();
		}
		return menus.stream().map(MenuDTO::new).collect(Collectors.toList());
	}

	/**
	 * 특정 메뉴를 수정
	 * 
	 * @param menuDTO 수정할 메뉴 정보를 담은 DTO 객체
	 * @return MenuDTO 수정된 메뉴 정보를 반환
	 * @throws MenuExceptions 해당 메뉴가 존재하지 않을 경우 발생하는 예외
	 */
	@Transactional
	public MenuDTO updateMenu(MenuDTO menuDTO) {
		MenuEntity existingMenu = menuRepository.findById(menuDTO.getId())
			.orElseThrow(() -> MenuExceptions.MENU_NOT_FOUND.get());

		existingMenu.changeMenuName(menuDTO.getMenuName());
		existingMenu.changeMenuPrice(menuDTO.getMenuPrice());
		existingMenu.changeIsActive(menuDTO.isActive());
		existingMenu.setModifiedId(menuDTO.getModifiedId());
		existingMenu.setModifiedDate(LocalDateTime.now());
		
		if (menuDTO.getMenuCategory() != null && menuDTO.getMenuCategory().getNo() != null) {
			MenuCategoryEntity newCategory = menuCategoryRepository.findById(menuDTO.getMenuCategory().getNo())
				.orElseThrow(() -> MenuCategoryExceptions.MENU_CATEGORY_NOT_FOUND.get());
			existingMenu.setMenuCategory(newCategory);
		}

		return new MenuDTO(menuRepository.save(existingMenu));
	}

	/**
	 * 새로운 메뉴를 추가
	 * 
	 * @param menuDTO 추가할 메뉴 정보를 담은 DTO 객체
	 * @return MenuDTO 추가된 메뉴 정보를 반환
	 * @throws MenuExceptions 메뉴 이름 중복 또는 카테고리 미등록 시 발생하는 예외
	 */
	@Transactional
	public MenuDTO addMenu(MenuDTO menuDTO) {
		if (menuRepository.existsByMenuName(menuDTO.getMenuName())) {
			throw MenuExceptions.MENU_NAME_DUPLICATED.get();
		}

		if (menuDTO.getMenuCategory() != null && menuDTO.getMenuCategory().getNo() != null) {
			MenuCategoryEntity menuCategory = menuCategoryRepository.findById(menuDTO.getMenuCategory().getNo())
				.orElseThrow(() -> MenuCategoryExceptions.MENU_CATEGORY_NOT_FOUND.get());

			MenuEntity newMenu = MenuEntity.builder()
				.menuName(menuDTO.getMenuName())
				.menuPrice(menuDTO.getMenuPrice())
				.isActive(menuDTO.isActive())
				.createdId(menuDTO.getCreatedId())
				.createdDate(LocalDateTime.now())
				.modifiedId(menuDTO.getModifiedId())
				.menuCategory(menuCategory)
				.build();

			return new MenuDTO(menuRepository.save(newMenu));
		} else {
			throw MenuExceptions.MENU_NOT_REGISTERED.get();
		}
	}

	/**
	 * 특정 메뉴를 삭제
	 * 
	 * @param id 삭제할 메뉴의 ID(Long)
	 * @throws MenuExceptions 해당 메뉴가 존재하지 않을 경우 발생하는 예외
	 */
	@Transactional
	public void deleteMenu(Long id) {
		MenuEntity menuEntity = menuRepository.findById(id)
				.orElseThrow(() -> MenuExceptions.MENU_NOT_FOUND.get());
		menuRepository.delete(menuEntity);
	}
}
