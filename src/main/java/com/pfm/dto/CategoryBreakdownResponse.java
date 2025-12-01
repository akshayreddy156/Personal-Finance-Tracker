package com.pfm.dto;

import java.util.List;

public class CategoryBreakdownResponse {
	private Long userId;
	private Integer year;
	private Integer month;
	private List<CategoryBreakdownItem> breakdown;

	public CategoryBreakdownResponse() {
	}

	public CategoryBreakdownResponse(Long userId, Integer year, Integer month, List<CategoryBreakdownItem> breakdown) {
		this.userId = userId;
		this.year = year;
		this.month = month;
		this.breakdown = breakdown;
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

	public Integer getMonth() {
		return month;
	}

	public void setMonth(Integer month) {
		this.month = month;
	}

	public List<CategoryBreakdownItem> getBreakdown() {
		return breakdown;
	}

	public void setBreakdown(List<CategoryBreakdownItem> breakdown) {
		this.breakdown = breakdown;
	}

}
