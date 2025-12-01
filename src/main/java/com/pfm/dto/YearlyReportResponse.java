package com.pfm.dto;

import java.math.BigDecimal;
import java.util.List;

public class YearlyReportResponse {
	private Long userId;
	private Integer startYear;
	private Integer endYear;
	private List<YearSummary> years;
	private BigDecimal totalIncome;
	private BigDecimal totalExpense;
	private BigDecimal balance;

	public YearlyReportResponse() {
	}

	public YearlyReportResponse(Long userId, Integer startYear, Integer endYear, List<YearSummary> years,
			BigDecimal totalIncome, BigDecimal totalExpense, BigDecimal balance) {
		this.userId = userId;
		this.startYear = startYear;
		this.endYear = endYear;
		this.years = years;
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

	public Integer getStartYear() {
		return startYear;
	}

	public void setStartYear(Integer startYear) {
		this.startYear = startYear;
	}

	public Integer getEndYear() {
		return endYear;
	}

	public void setEndYear(Integer endYear) {
		this.endYear = endYear;
	}

	public List<YearSummary> getYears() {
		return years;
	}

	public void setYears(List<YearSummary> years) {
		this.years = years;
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
