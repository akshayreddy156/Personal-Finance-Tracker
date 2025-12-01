package com.pfm.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.pfm.enums.AmountType;

public class TransactionResponse {

	private Long id;
	private BigDecimal  Amount;
	private AmountType type;
	private String description;
	private LocalDateTime date;
	private CategoryResponse category;
	private Long userId;
	private LocalDateTime createdAt;

	public TransactionResponse(Long id, BigDecimal  amount, AmountType type, String description, LocalDateTime date,
			CategoryResponse category, Long userId, LocalDateTime createdAt) {
		super();
		this.id = id;
		Amount = amount;
		this.type = type;
		this.description = description;
		this.date = date;
		this.category = category;
		this.userId = userId;
		this.createdAt = createdAt;
	}

	public TransactionResponse() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal  getAmount() {
		return Amount;
	}

	public void setAmount(BigDecimal  amount) {
		Amount = amount;
	}

	public AmountType getType() {
		return type;
	}

	public void setType(AmountType type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public CategoryResponse getCategory() {
		return category;
	}

	public void setCategory(CategoryResponse category) {
		this.category = category;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

}
