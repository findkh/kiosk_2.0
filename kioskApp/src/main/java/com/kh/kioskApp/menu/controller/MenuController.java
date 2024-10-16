package com.kh.kioskApp.menu.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.kioskApp.MenuCategory.exception.MenuCategoryTaskException;
import com.kh.kioskApp.menu.dto.MenuDTO;
import com.kh.kioskApp.menu.exception.MenuTaskException;
import com.kh.kioskApp.menu.service.MenuService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/menu")
@Tag(name="Menu", description = "메뉴 관리 API")
@RequiredArgsConstructor
public class MenuController {
	
	private final MenuService menuService;
	
	@Operation(summary = "모든 메뉴 조회", description = "모든 메뉴를 조회합니다.")
	@GetMapping("/list")
	public ResponseEntity<Map<String, Object>> getAllMenus() {
		Map<String, Object> response = new HashMap<>();
		try {
			List<MenuDTO> result = menuService.getAllMenus();
			response.put("allMenus", result);
			return ResponseEntity.ok(response);
		} catch (MenuTaskException e) {
			return handleException(e.getMsg(), e.getCode());
		}
	}
	
	@Operation(summary = "카테고리별 메뉴 조회", description = "카테고리 번호로 해당 카테고리에 맞는 메뉴를 조회합니다.")
	@GetMapping("/category/{categoryId}")
	public ResponseEntity<Map<String, Object>> getMenusByCategoryId(@PathVariable("categoryId") Long categoryId) {
		Map<String, Object> response = new HashMap<>();
		try {
			List<MenuDTO> result = menuService.getMenusByCategoryId(categoryId);
			response.put("MenuList", result);
			return ResponseEntity.ok(response);
		} catch (MenuTaskException e) {
			return handleException(e.getMsg(), e.getCode());
		}
	}
	
	@Operation(summary = "메뉴 수정", description = "메뉴 ID로 메뉴 정보를 수정합니다.")
	@PutMapping("/{id}")
	public ResponseEntity<Map<String, Object>> updateMenu(@RequestBody MenuDTO menuDTO) {
		Map<String, Object> response = new HashMap<>();
		try {
			MenuDTO updatedMenu = menuService.updateMenu(menuDTO);
			response.put("updatedMenu", updatedMenu);
			return ResponseEntity.ok(response);
		} catch (MenuTaskException e) {
			return handleException(e.getMsg(), e.getCode());
		}
	}
	
	@Operation(summary = "메뉴 추가", description = "새로운 메뉴를 추가합니다.")
	@PostMapping("/add")
	public ResponseEntity<Map<String, Object>> addMenu(@RequestBody MenuDTO menuDTO) {
		Map<String, Object> response = new HashMap<>();
		try {
			MenuDTO createdMenu = menuService.addMenu(menuDTO);
			response.put("menu", createdMenu);
			return ResponseEntity.status(HttpStatus.CREATED).body(response);
		} catch (MenuTaskException e) {
			response.put("message", e.getMsg());
			return handleException(e.getMsg(), e.getCode());
		} catch (MenuCategoryTaskException e) {
			response.put("message", e.getMsg());
			return handleException(e.getMsg(), e.getCode());
		}
	}
	
	@DeleteMapping("/menu/{id}")
	public ResponseEntity<Map<String, String>> deleteMenu(@PathVariable("id") Long id) {
		Map<String, String> response = new HashMap<>();
		menuService.deleteMenu(id);
		response.put("message", "SUCCESS");
		return ResponseEntity.ok(response);
	}
	
	private ResponseEntity<Map<String, Object>> handleException(String msg, int status){
		return ResponseEntity.status(status).body(Map.of("error", msg));
	}
}
