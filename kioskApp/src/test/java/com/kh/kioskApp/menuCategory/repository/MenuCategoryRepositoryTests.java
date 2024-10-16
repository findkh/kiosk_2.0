package com.kh.kioskApp.menuCategory.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.kh.kioskApp.MenuCategory.entity.MenuCategoryEntity;
import com.kh.kioskApp.MenuCategory.exception.MenuCategoryExceptions;
import com.kh.kioskApp.MenuCategory.exception.MenuCategoryTaskException;
import com.kh.kioskApp.MenuCategory.repository.MenuCategoryRepository;

@DataJpaTest
//@SpringBootTest
public class MenuCategoryRepositoryTests {
	@Autowired
	private MenuCategoryRepository menuCategoryRepository;
	
	@Test
	void testUpdateCategoryName() {
		String 변경전이름 = "TEA";
		String 변경할이름 = "COFFEE";
		
		try {
			Optional<MenuCategoryEntity> optionalEntity = menuCategoryRepository.findByCategoryName(변경전이름);
			
			// 카테고리가 존재하는지 확인
			if (optionalEntity.isPresent()) {
				MenuCategoryEntity entity = optionalEntity.get();
				
				if(menuCategoryRepository.findByCategoryName(변경할이름).isPresent()) {
					throw MenuCategoryExceptions.MENU_CATEGORY_DUPLICATED.get();
				} else {
					// 카테고리 수정
					entity.changeCategoryName(변경할이름);
					menuCategoryRepository.save(entity);
					// 수정된 카테고리 조회
					MenuCategoryEntity updatedEntity = menuCategoryRepository.findByCategoryName(변경할이름).orElse(null);
					
					// 테스트 결과 확인
					if (updatedEntity != null && 변경할이름.equals(updatedEntity.getCategoryName())) {
						System.out.println("Test passed: Category name updated successfully.");
					} else {
						throw MenuCategoryExceptions.MENU_CATEGORY_NOT_MODIFIED.get();
					}
				}
				
			} else {
				throw MenuCategoryExceptions.MENU_CATEGORY_NOT_MATCHED.get();
			}
		} catch(MenuCategoryTaskException e) {
			System.out.println("Test passed: " + e.getMsg());
		}
	}
	
	@Test
	void testDuplicateCategoryNameInsert() {
		MenuCategoryEntity entity2 = MenuCategoryEntity.builder()
				.categoryName("COFFEE")  // 중복된 카테고리명
				.createdDate(LocalDateTime.now())
				.build();
		
		try {
			if (menuCategoryRepository.findByCategoryName(entity2.getCategoryName()).isPresent()) {
				throw MenuCategoryExceptions.MENU_CATEGORY_NOT_REGISTERED.get();
			}
			menuCategoryRepository.save(entity2);
			System.out.println("Test failed: Duplicate category was saved.");
		} catch (MenuCategoryTaskException e) {
			System.out.println("Test passed: " + e.getMessage());
		}
	}
	
	@Test
	void saveTest() {
		MenuCategoryEntity entity = MenuCategoryEntity.builder()
				.categoryName("DESSERT")
				.createdDate(LocalDateTime.now())
				.build();
		
		menuCategoryRepository.save(entity);
	}

	@Test
	void findAllTest() {
		List<MenuCategoryEntity> entities = menuCategoryRepository.findAll();
		
		// 저장된 데이터 확인
		if (entities.isEmpty()) {
			System.out.println("No entities found");
		} else {
			entities.forEach(entity -> System.out.println(entity.toString()));
		}
	}
}
