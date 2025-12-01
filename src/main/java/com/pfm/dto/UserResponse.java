package com.pfm.dto;

import java.math.BigDecimal;

public class UserResponse {
	
	private long userId;
	private String name;
	private String email;
	private BigDecimal  monthlyAmount;
	public UserResponse() {
		super();
	}
	public UserResponse(long userId, String name, String email, BigDecimal  monthlyAmount) {
		super();
		this.userId = userId;
		this.name = name;
		this.email = email;
		this.monthlyAmount = monthlyAmount;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public BigDecimal  getMonthlyAmount() {
		return monthlyAmount;
	}
	public void setMonthlyAmount(BigDecimal  monthlyAmount) {
		this.monthlyAmount = monthlyAmount;
	}

}
