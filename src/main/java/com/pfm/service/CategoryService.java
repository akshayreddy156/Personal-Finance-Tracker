package com.pfm.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.pfm.dto.CategoryCreateRequest;
import com.pfm.dto.CategoryResponse;
import com.pfm.dto.CategoryUpdateRequest;

@Service
public interface CategoryService {

	CategoryResponse createCategory(CategoryCreateRequest req, Long currentUserId);

	List<CategoryResponse> listCategories(Long currentUserId);

	CategoryResponse updateCategory(Long categoryId, CategoryUpdateRequest req, Long currentUserId);

	void deleteCategory(Long categoryId, Long currentUserId);

}
