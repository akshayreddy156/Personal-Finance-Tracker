package com.pfm.dto;

import com.pfm.enums.AmountType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CategoryCreateRequest {

	@NotBlank
	private String categoryName;
	
	@NotNull
	private AmountType type;

	public CategoryCreateRequest(@NotBlank String categoryName, @NotNull AmountType type) {
		super();
		this.categoryName = categoryName;
		this.type = type;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public AmountType getType() {
		return type;
	}

	public void setType(AmountType type) {
		this.type = type;
	} 
	
}
