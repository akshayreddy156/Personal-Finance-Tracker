package com.pfm.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pfm.dto.CategoryResponse;
import com.pfm.dto.TransactionCreateRequest;
import com.pfm.dto.TransactionResponse;
import com.pfm.enums.AmountType;
import com.pfm.exceptions.AuthorizationException;
import com.pfm.exceptions.NotFoundException;
import com.pfm.model.Category;
import com.pfm.model.Transaction;
import com.pfm.model.User;
import com.pfm.repository.CategoryRepository;
import com.pfm.repository.TransactionRepository;
import com.pfm.repository.UserRepository;

@Service
public class TransactionServiceImpl implements TransactionService {

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private TransactionRepository transactionRepo;

	@Autowired
	private CategoryRepository categoryRepo;

	private TransactionResponse toResponse(Transaction saved) {
		TransactionResponse resp = new TransactionResponse();
		resp.setId(saved.getUser().getUserId());
		resp.setAmount(saved.getAmount());
		resp.setType(saved.getType());
		resp.setDescription(saved.getDescription());
		resp.setDate(saved.getDate());
		if (saved.getCreatedAt() != null)
			resp.setCreatedAt(saved.getCreatedAt());
		if (saved.getCategory() != null) {
			Category c = saved.getCategory();
			resp.setCategory(new CategoryResponse(c.getCategoryId(), c.getCategoryName(), c.getType(),
					c.getUser() != null ? c.getUser().getUserId() : null));
		}
		resp.setUserId(saved.getUser() != null ? saved.getUser().getUserId() : null);
		return resp;
	}

	private Page<TransactionResponse> listToPage(List<Transaction> list, Pageable pageRequest) {
		int total = list.size();
		int pageSize = pageRequest.getPageSize();
		int pageNumber = pageRequest.getPageNumber();
		int fromIndex = pageNumber * pageSize;
		if (fromIndex >= total) {
			return new PageImpl<>(List.of(), pageRequest, total);
		}
		int toIndex = Math.min(fromIndex + pageSize, total);
		List<TransactionResponse> content = list.subList(fromIndex, toIndex).stream().map(this::toResponse)
				.collect(Collectors.toList());
		return new PageImpl<>(content, pageRequest, total);
	}

	private BigDecimal applyEffect(BigDecimal balance, AmountType type, BigDecimal amount) {
		if (balance == null)
			balance = BigDecimal.ZERO;
		if (amount == null)
			amount = BigDecimal.ZERO;
		return type == AmountType.INCOME ? balance.add(amount) : balance.subtract(amount);
	}

	private BigDecimal reverseEffect(BigDecimal balance, AmountType type, BigDecimal amount) {
		// reverse the effect: if type was EXPENSE (we previously subtracted), we add it
		// back
		if (balance == null)
			balance = BigDecimal.ZERO;
		if (amount == null)
			amount = BigDecimal.ZERO;
		return type == AmountType.INCOME ? balance.subtract(amount) : balance.add(amount);
	}

	@Override
	@Transactional
	public TransactionResponse createTransaction(TransactionCreateRequest req, Long currentUserId) {
		User user = userRepo.findById(currentUserId)
				.orElseThrow(() -> new UsernameNotFoundException("User not found with" + currentUserId));

		Category category = categoryRepo.findById(req.getCategoryId())
				.orElseThrow(() -> new NotFoundException("Category not found with id: " + req.getCategoryId()));

		if (category.getUser() == null || !(category.getUser().getUserId() == (currentUserId))) {
			throw new AuthorizationException("Category does not belong to the current user");
		}

		Transaction t = new Transaction();
		t.setAmount(req.getAmount());
		t.setType(req.getType());
		t.setDescription(req.getDescription());
		LocalDateTime date = req.getDate() == null ? LocalDateTime.now() : req.getDate();
		t.setDate(date);
		t.setCategory(category);
		t.setUser(user);

		Transaction saved = transactionRepo.save(t);

		user.setAvailableBalance(applyEffect(user.getAvailableBalance(), saved.getType(), saved.getAmount()));

		// persist user within same transaction
		userRepo.save(user); // optimistic locking will apply if version changed
		return toResponse(saved);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<TransactionResponse> listTransactions(Long currentUserId, LocalDate start, LocalDate end,
			Long categoryId, Pageable pageRequest) {

		// CASE 1 – No filters → use pageable directly (best performance)
		if (start == null && end == null && categoryId == null) {
			Page<Transaction> page = transactionRepo.findByUser_UserId(currentUserId, pageRequest);
			return page.map(this::toResponse);
		}

		// Normalize LocalDate -> LocalDateTime range (cover entire days)
		LocalDateTime startDT = (start != null) ? start.atStartOfDay() : LocalDateTime.of(1970, 1, 1, 0, 0);
		LocalDateTime endDT = (end != null) ? end.atTime(LocalTime.MAX) : LocalDateTime.now();

		// Fetch matching by date range (repository returns List<Transaction>)
		List<Transaction> list = transactionRepo.findByUser_UserIdAndDateBetween(currentUserId, startDT, endDT);

		// Filter by category if needed (use safe null checks and .equals)
		if (categoryId != null) {
			list = list.stream().filter(tr -> tr.getCategory() != null && tr.getCategory().getCategoryId() != null
					&& tr.getCategory().getCategoryId().equals(categoryId)).collect(Collectors.toList());
		}

		// Sort by date desc (safe comparator)
		list.sort(Comparator.comparing(Transaction::getDate).reversed());

		// Convert to Page<TransactionResponse> using existing helper
		return listToPage(list, pageRequest);
	}

	@Override
	public void deleteTransaction(Long id, Long currentUserId) {
		Transaction tr = transactionRepo.findById(id)
				.orElseThrow(() -> new NotFoundException("Transaction Id not found with" + id));
		if (tr.getUser() != null || !tr.getUser().getUserId().equals(currentUserId)) {
			throw new AuthorizationException("You're not authorized to Delete this Transaction");
		}
		 User user = tr.getUser();

		    // Reverse effect
		    user.setAvailableBalance(reverseEffect(user.getAvailableBalance(), tr.getType(), tr.getAmount()));

		    // Delete transaction and save user in same transaction
		    transactionRepo.delete(tr);
		    userRepo.save(user);

	}

}
