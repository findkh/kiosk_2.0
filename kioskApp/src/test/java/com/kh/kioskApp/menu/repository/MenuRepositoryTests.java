package com.kh.kioskApp.menu.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.kh.kioskApp.MenuCategory.entity.MenuCategoryEntity;
import com.kh.kioskApp.MenuCategory.repository.MenuCategoryRepository;
import com.kh.kioskApp.menu.entity.MenuEntity;

//@SpringBootTest
@DataJpaTest
public class MenuRepositoryTests {
	@Autowired
	private MenuRepository menuRepository;

	@Autowired
	private MenuCategoryRepository menuCategoryRepository;
	
	@Test
	public void deleteMenu() {
		Long menuId = 1L; // 삭제할 메뉴 ID

		// 기존 메뉴 엔티티 조회
		MenuEntity menuEntity = menuRepository.findById(menuId).orElse(null);
		assertNotNull(menuEntity, "삭제할 메뉴가 존재해야 합니다.");

		// 메뉴 삭제
		menuRepository.delete(menuEntity);

		// 삭제 후 메뉴 조회
		boolean exists = menuRepository.existsById(menuId);
		
		// 삭제된 메뉴가 존재하지 않음을 검증
		assertFalse(exists, "삭제된 메뉴는 존재하지 않아야 합니다.");
	}
	
	@Test
	public void updateMenu() {
		Long menuId = 1L; // 업데이트할 메뉴 ID
		Long newCategoryId = 33L; // 변경할 카테고리 ID
		String newName = "COFFEE 메뉴 1 변경함"; // 변경할 메뉴 이름
		Integer newPrice = 9999; // 변경할 메뉴 가격

		// 기존 메뉴 엔티티 조회
		MenuEntity menuEntity = menuRepository.findById(menuId).orElse(null);
		assertNotNull(menuEntity, "업데이트할 메뉴가 존재해야 합니다.");

		// 메뉴 정보 업데이트
		menuEntity.setMenuName(newName);
		menuEntity.setMenuPrice(newPrice);
		menuEntity.getMenuCategory().setNo(newCategoryId); // 카테고리 변경 (ID를 변경해야 함)
		
		// 메뉴 업데이트
		menuRepository.save(menuEntity);

		// 업데이트된 메뉴 조회
		MenuEntity updatedMenuEntity = menuRepository.findById(menuId).orElse(null);
		assertNotNull(updatedMenuEntity, "업데이트된 메뉴를 다시 조회할 수 있어야 합니다.");

		// 업데이트 결과 검증
		assertEquals(newName, updatedMenuEntity.getMenuName(), "메뉴 이름이 올바르게 업데이트되어야 합니다.");
		assertEquals(newPrice, updatedMenuEntity.getMenuPrice(), "메뉴 가격이 올바르게 업데이트되어야 합니다.");
		assertEquals(newCategoryId, updatedMenuEntity.getMenuCategory().getNo(), "메뉴 카테고리가 올바르게 업데이트되어야 합니다.");
	}
	
	@Test
	public void selectMenusByCategoryId() {
		// 특정 카테고리 ID로 메뉴 조회 (예: 카테고리 ID 1번)
		Long categoryId = 1L;
		List<MenuEntity> menus = menuRepository.findByMenuCategoryNo(categoryId);
		
		// 조회된 메뉴 출력
		if (menus.isEmpty()) {
			System.out.println("조회된 메뉴가 없습니다. 카테고리 ID: " + categoryId);
		} else {
			menus.forEach(menu -> {
				System.out.println("메뉴 이름: " + menu.getMenuName());
				System.out.println("가격: " + menu.getMenuPrice());
				System.out.println("카테고리: " + menu.getMenuCategory().getCategoryName());
				System.out.println("생성일: " + menu.getCreatedDate());
				System.out.println("=================================");
			});
		}
		
		// 메뉴가 조회되었는지 확인
		assertFalse(menus.isEmpty(), "해당 카테고리의 메뉴 목록이 비어있지 않아야 합니다.");
	}
	
	@Test
	public void selectAllMenus() {
		// 모든 메뉴 조회
		List<MenuEntity> menus = menuRepository.findAll();
		
		// 조회된 메뉴 출력 (간단한 확인을 위해 콘솔에 출력)
		menus.forEach(menu -> {
			System.out.println("메뉴 이름: " + menu.getMenuName());
			System.out.println("가격: " + menu.getMenuPrice());
			System.out.println("카테고리: " + menu.getMenuCategory().getCategoryName());
			System.out.println("생성일: " + menu.getCreatedDate());
			System.out.println("=================================");
		});
		
		// 실제 테스트에서는 assertEquals나 assertNotNull 같은 검증 로직을 추가할 수 있음
		assertFalse(menus.isEmpty(), "메뉴 목록이 비어있지 않아야 합니다.");
	}
	
	@Test
	public void insertMenusForEachCategory() {
		// 카테고리 목록 조회
		List<MenuCategoryEntity> categories = menuCategoryRepository.findAll();
		
		// 각 카테고리마다 5개의 메뉴 삽입
		categories.forEach(category -> {
			IntStream.range(1, 6).forEach(i -> {
				MenuEntity menu = MenuEntity.builder()
					.menuName(category.getCategoryName() + " 메뉴 " + i)  // 카테고리 이름 + 메뉴 번호로 이름 생성
					.menuPrice(1000 * i)  // 가격은 1000원 단위로 설정
					.isActive(true)  // 기본 활성화 상태
					.createdId("admin@test.com")
					.createdDate(java.time.LocalDateTime.now())
					.menuCategory(category)  // 카테고리 설정
					.build();
				
				// 메뉴 저장
				menuRepository.save(menu);
			});
		});
	}
}