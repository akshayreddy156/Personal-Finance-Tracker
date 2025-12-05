package com.pfm.dto;

import java.math.BigDecimal;

public class UserResponse {
	
	private long userId;
	private String name;
	private String email;
	private BigDecimal  monthlyIncome;
	private BigDecimal  availableBalance;
	public UserResponse() {
		super();
	}
	public UserResponse(long userId, String name, String email, BigDecimal  monthlyIncome,BigDecimal  availableBalance) {
		super();
		this.userId = userId;
		this.name = name;
		this.email = email;
		this.monthlyIncome = monthlyIncome;
		this.availableBalance=availableBalance;
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
	public BigDecimal  getMonthlyIncome() {
		return monthlyIncome;
	}
	public void setMonthlyIncome(BigDecimal  monthlyIncome) {
		this.monthlyIncome = monthlyIncome;
	}
	public BigDecimal getAvailableBalance() {
		return availableBalance;
	}
	public void setAvailableBalance(BigDecimal availableBalance) {
		this.availableBalance = availableBalance;
	}

}
