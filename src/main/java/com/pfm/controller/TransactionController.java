package com.pfm.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pfm.dto.TransactionCreateRequest;
import com.pfm.dto.TransactionResponse;
import com.pfm.security.CustomUserDetails;
import com.pfm.service.TransactionService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

	@Autowired
	private TransactionService transactionService;

	@GetMapping
	public Page<TransactionResponse> list(@AuthenticationPrincipal CustomUserDetails principal,
			@RequestParam(required = false) String start, @RequestParam(required = false) String end,
			@RequestParam(required = false) Long categoryId, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "20") int size) {
		LocalDate s = start == null ? null : LocalDate.parse(start);
		LocalDate e = end == null ? null : LocalDate.parse(end);
		Pageable p = PageRequest.of(page, size, Sort.by("date").descending());
		return transactionService.listTransactions(principal.getUserId(), s, e, categoryId, p);
	}

	@PostMapping
	public ResponseEntity<TransactionResponse> create(@AuthenticationPrincipal CustomUserDetails principal,
			@Valid @RequestBody TransactionCreateRequest req) {
		TransactionResponse tres = transactionService.createTransaction(req, principal.getUserId());
		return ResponseEntity.status(HttpStatus.CREATED).body(tres);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@AuthenticationPrincipal CustomUserDetails principal, @PathVariable Long id) {
		transactionService.deleteTransaction(id, principal.getUserId());
		return ResponseEntity.noContent().build();
	}

}
