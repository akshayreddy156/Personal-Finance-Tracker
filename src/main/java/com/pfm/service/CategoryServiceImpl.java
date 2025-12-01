package com.pfm.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pfm.dto.CategoryCreateRequest;
import com.pfm.dto.CategoryResponse;
import com.pfm.dto.CategoryUpdateRequest;
import com.pfm.exceptions.AuthorizationException;
import com.pfm.exceptions.NotFoundException;
import com.pfm.model.Category;
import com.pfm.model.User;
import com.pfm.repository.CategoryRepository;
import com.pfm.repository.UserRepository;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepository categoryRepo;

	@Autowired
	private UserRepository userRepo;

	private CategoryResponse toResponse(Category c) {
		CategoryResponse resp = new CategoryResponse();
		resp.setId(c.getCategoryId());
		resp.setName(c.getCategoryName());
		resp.setType(c.getType());
		if (c.getUser() != null)
			resp.setUserId(c.getUser().getUserId());
		return resp;
	}

	@Override
	@Transactional
	public CategoryResponse createCategory(CategoryCreateRequest req, Long currentUserId) {
		User user = userRepo.findById(currentUserId)
				.orElseThrow(() -> new UsernameNotFoundException("User not found with" + currentUserId));
		Category category = new Category();
		category.setCategoryName(req.getCategoryName());
		category.setType(req.getType());
		category.setUser(user);
		Category saved = categoryRepo.save(category);
		return toResponse(saved);
	}

	@Override
	public List<CategoryResponse> listCategories(Long currentUserId) {
		List<Category> list = categoryRepo.findByUser_UserId(currentUserId);
		return list.stream().map(this::toResponse).collect(Collectors.toList());
	}

	@Override
	public CategoryResponse updateCategory(Long categoryId, CategoryUpdateRequest req, Long currentUserId) {
		Category category = categoryRepo.findById(categoryId)
				.orElseThrow(() -> new NotFoundException("Category not found with" + categoryId));
		if (category.getUser() == null || !(category.getUser().getUserId() == (currentUserId))) {
			throw new AuthorizationException("You're not authorized to update this category");
		}
		category.setCategoryName(req.getCategoryName());
		category.setType(req.getType());
		Category saved = categoryRepo.save(category);
		return toResponse(saved);
	}

	@Override
	public void deleteCategory(Long categoryId, Long currentUserId) {

		categoryRepo.findById(categoryId).ifPresentOrElse(cat -> {
			if (cat.getUser() == null || !(cat.getUser().getUserId() == (currentUserId))) {
				throw new AuthorizationException("You're not authorized to Delete this category");
			}
			categoryRepo.delete(cat);
		}, () -> {
			throw new NotFoundException("Category not found with id: " + categoryId);
		});
	}

}
