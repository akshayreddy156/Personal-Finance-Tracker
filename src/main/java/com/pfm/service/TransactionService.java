package com.pfm.service;

import java.time.LocalDate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.pfm.dto.TransactionCreateRequest;
import com.pfm.dto.TransactionResponse;

@Service
public interface TransactionService {

	TransactionResponse createTransaction(TransactionCreateRequest req, Long currentUserId);

	Page<TransactionResponse> listTransactions(Long currentUserId, LocalDate start, LocalDate end, Long categoryId,
			Pageable pageRequest);

	void deleteTransaction(Long id, Long currentUserId);

}
