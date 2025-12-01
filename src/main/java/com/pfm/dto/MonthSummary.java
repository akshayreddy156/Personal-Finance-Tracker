package com.pfm.dto;

import java.math.BigDecimal;

public class MonthSummary {
	private int month; // 1..12
	private BigDecimal income;
	private BigDecimal expense;

	public MonthSummary() {
	}

	public MonthSummary(int month, BigDecimal income, BigDecimal expense) {
		this.month = month;
		this.income = income;
		this.expense = expense;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
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
