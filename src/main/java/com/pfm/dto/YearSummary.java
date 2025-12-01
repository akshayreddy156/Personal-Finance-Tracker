package com.pfm.dto;

import java.math.BigDecimal;

public class YearSummary {
	private int year;
	private BigDecimal income;
	private BigDecimal expense;

	public YearSummary() {
	}

	public YearSummary(int year, BigDecimal income, BigDecimal expense) {
		this.year = year;
		this.income = income;
		this.expense = expense;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public BigDecimal getIncome() {
		return income;
	}

	public void setIncome(BigDecimal income) {
		this.income = income;
	}

	public BigDecimal getExpense() {
		return expense;
	}

	public void setExpense(BigDecimal expense) {
		this.expense = expense;
	}
}
