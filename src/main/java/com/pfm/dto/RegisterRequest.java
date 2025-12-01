package com.pfm.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class RegisterRequest {

	@NotBlank
	private String  name;
	
	@NotBlank
	@Email
	private String email;
	
	@NotBlank
	private String password;
	
	@NotNull
	@DecimalMin("0.00")
	private BigDecimal  monthlyIncome;

	public RegisterRequest() {
		super();
	}

	public RegisterRequest(@NotBlank String name, @NotBlank @Email  String email,
			@NotBlank String password, @NotNull @DecimalMin("0.00") BigDecimal  monthlyIncome) {
		super();
		this.name = name;
		this.email = email;
		this.password = password;
		this.monthlyIncome = monthlyIncome;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public BigDecimal  getMonthlyIncome() {
		return monthlyIncome;
	}

	public void setMonthlyIncome(BigDecimal  monthlyIncome) {
		this.monthlyIncome = monthlyIncome;
	}
	
	
	
}
