package com.pfm.dto;

import com.pfm.enums.AmountType;

public class CategoryResponse {

	private Long id;
	private String name;
	private AmountType type;
	private Long userId;

	public CategoryResponse() {
		super();
	}

	public CategoryResponse(Long id, String name, AmountType type, Long userId) {
		super();
		this.id = id;
		this.name = name;
		this.type = type;
		this.userId = userId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public AmountType getType() {
		return type;
	}

	public void setType(AmountType type) {
		this.type = type;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

}
