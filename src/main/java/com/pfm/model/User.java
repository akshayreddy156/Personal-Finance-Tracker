package com.pfm.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userId;
	private String name;
	@Column(unique = true)
	private String email;
	private String passwordHash;
	@Column(nullable = false, precision = 12, scale = 2)
	private BigDecimal  monthlyIncome;
	
	@CreationTimestamp
	@Column(nullable = false, updatable = false)
	private LocalDateTime createdAt;
	
	
	public User() {
		super();
	}
	public User(Long userId, String name, String email, String passwordHash, BigDecimal  monthlyIncome, LocalDateTime createdAt) {
		super();
		this.name=name;
		this.userId = userId;
		this.email = email;
		this.passwordHash = passwordHash;
		this.monthlyIncome = monthlyIncome;
		this.createdAt = createdAt;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPasswordHash() {
		return passwordHash;
	}
	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}
	
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public BigDecimal  getMonthlyIncome() {
		return monthlyIncome;
	}
	public void setMonthlyAmount(BigDecimal  monthlyIncome) {
		this.monthlyIncome = monthlyIncome;
	}
	
	

}
