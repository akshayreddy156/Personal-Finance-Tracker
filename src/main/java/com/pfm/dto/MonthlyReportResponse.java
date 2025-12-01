package com.pfm.dto;

import java.math.BigDecimal;
import java.util.List;

public class MonthlyReportResponse {

	private Long userId;
	private Integer year;
	private List<MonthSummary> months; // 12 entries
	private BigDecimal totalIncome;
	private BigDecimal totalExpense;
	private BigDecimal balance;

	// getters / setters / constructors
	public MonthlyReportResponse() {
	}

	public MonthlyReportResponse(Long userId, Integer year, List<MonthSummary> months, BigDecimal totalIncome,
			BigDecimal totalExpense, BigDecimal balance) {
		this.userId = userId;
		this.year = year;
		this.months = months;
		this.totalIncome = totalIncome;
		this.totalExpense = totalExpense;
		this.balance = balance;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public List<MonthSummary> getMonths() {
		return months;
	}

	public void setMonths(List<MonthSummary> months) {
		this.months = months;
	}

	public BigDecimal getTotalIncome() {
		return totalIncome;
	}

	public void setTotalIncome(BigDecimal totalIncome) {
		this.totalIncome = totalIncome;
	}

	public BigDecimal getTotalExpense() {
		return totalExpense;
	}

	public void setTotalExpense(BigDecimal totalExpense) {
		this.totalExpense = totalExpense;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

}