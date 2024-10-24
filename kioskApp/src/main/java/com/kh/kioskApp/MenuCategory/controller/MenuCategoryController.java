package com.kh.kioskApp.MenuCategory.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.kioskApp.MenuCategory.dto.MenuCategoryDTO;
import com.kh.kioskApp.MenuCategory.dto.UpdateMenuCategoryDTO;
import com.kh.kioskApp.MenuCategory.entity.MenuCategoryEntity;
import com.kh.kioskApp.MenuCategory.service.MenuCategoryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/menuCategory")
@Tag(name ="MenuCategory", description = "메뉴 카테고리 관리 API")
@RequiredArgsConstructor
public class MenuCategoryController {
	
	private final MenuCategoryService menuCategoryService;
	
	@Operation(summary = "모든 메뉴 카테고리 조회", description = "모든 메뉴 카테고리를 조회합니다.")
	@GetMapping("/list")
	public ResponseEntity<Map<String, Object>> getAllCategories() {
		Map<String, Object> response = new HashMap<>();
		List<MenuCategoryEntity> categories = menuCategoryService.getAllCategories();
		response.put("categories", categories);
		return ResponseEntity.ok(response);
	}
	
	@Operation(summary = "메뉴 카테고리 생성", description = "메뉴 카테고리를 생성합니다.")
	@PostMapping("/create")
	public ResponseEntity<Map<String, Object>> create(@RequestBody MenuCategoryDTO menuCategoryDTO) {
		menuCategoryService.createCategory(menuCategoryDTO.getCategoryName());
		List<MenuCategoryEntity> categories = menuCategoryService.getAllCategories();
		Map<String, Object> response = new HashMap<>();
		response.put("categories", categories);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	@Operation(summary = "메뉴 카테고리 수정", description = "메뉴 카테고리를 수정합니다.")
	@PutMapping("/update")
	public ResponseEntity<Map<String, Object>> update(@RequestBody UpdateMenuCategoryDTO menuCategoryDTO) {
		menuCategoryService.updateCategoryName(menuCategoryDTO.getNo(), menuCategoryDTO.getCategoryName());
		List<MenuCategoryEntity> categories = menuCategoryService.getAllCategories();
		Map<String, Object> response = new HashMap<>();
		response.put("categories", categories);
		return ResponseEntity.ok(response);
	}
}
