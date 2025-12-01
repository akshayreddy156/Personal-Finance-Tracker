package com.pfm.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.pfm.enums.AmountType;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class TransactionCreateRequest {
	
	@NotNull
	@DecimalMin("0.00")
	private BigDecimal  amount;
	
	@NotNull
	private AmountType type;
	
	@Size(max = 500)
	private String description;
	
	private LocalDateTime date;
	
	@NotNull
    private Long categoryId;

	public TransactionCreateRequest(@NotNull @DecimalMin("0.00") BigDecimal  amount, @NotNull AmountType type,
			@Size(max = 500) String description, @NotNull LocalDateTime date, @NotNull Long categoryId) {
		super();
		this.amount = amount;
		this.type = type;
		this.description = description;
		this.date = date;
		this.categoryId = categoryId;
	}

	public TransactionCreateRequest() {
		super();
	}

	public BigDecimal  getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal  amount) {
		this.amount = amount;
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

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}
	
}
