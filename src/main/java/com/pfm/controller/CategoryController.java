package com.pfm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pfm.dto.CategoryCreateRequest;
import com.pfm.dto.CategoryResponse;
import com.pfm.dto.CategoryUpdateRequest;
import com.pfm.security.CustomUserDetails;
import com.pfm.service.CategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	@GetMapping
	public List<CategoryResponse> list(@AuthenticationPrincipal CustomUserDetails principal) {
		return categoryService.listCategories(principal.getUserId());
	}

	@PostMapping
	public ResponseEntity<CategoryResponse> create(@AuthenticationPrincipal CustomUserDetails principal,
			@Valid @RequestBody CategoryCreateRequest req) {
		CategoryResponse res = categoryService.createCategory(req, principal.getUserId());
		return ResponseEntity.status(HttpStatus.CREATED).body(res);
	}

	@PutMapping("/{id}")
	public CategoryResponse update(@AuthenticationPrincipal CustomUserDetails principal, @PathVariable Long id,
			@Valid @RequestBody CategoryUpdateRequest req) {
		return categoryService.updateCategory(id, req, principal.getUserId());
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@AuthenticationPrincipal CustomUserDetails principal, @PathVariable Long id) {
		categoryService.deleteCategory(id, principal.getUserId());
		return ResponseEntity.noContent().build();
	}

}
