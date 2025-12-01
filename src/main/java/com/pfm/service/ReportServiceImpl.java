package com.pfm.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pfm.dto.CategoryBreakdownItem;
import com.pfm.dto.CategoryBreakdownResponse;
import com.pfm.dto.MonthSummary;
import com.pfm.dto.MonthlyReportResponse;
import com.pfm.dto.YearSummary;
import com.pfm.dto.YearlyReportResponse;
import com.pfm.enums.AmountType;
import com.pfm.projections.CategoryTotal;
import com.pfm.projections.MonthTotal;
import com.pfm.projections.YearTotal;
import com.pfm.repository.TransactionRepository;

@Service
public class ReportServiceImpl implements ReportService {

	@Autowired
	private TransactionRepository transactionRepo;

	public ReportServiceImpl(TransactionRepository transactionRepo) {
		this.transactionRepo = transactionRepo;
	}

	@Override
	@Transactional(readOnly = true)
	public MonthlyReportResponse monthlyReport(Long userId, Integer year) {
		// fetch totals per month for income & expense
		List<MonthTotal> incomeTotals = transactionRepo.findMonthlyTotalsForYear(userId, AmountType.INCOME, year);
		List<MonthTotal> expenseTotals = transactionRepo.findMonthlyTotalsForYear(userId, AmountType.EXPENSE, year);

		// prepare arrays 1..12
		BigDecimal[] incomeByMonth = new BigDecimal[12];
		BigDecimal[] expenseByMonth = new BigDecimal[12];
		Arrays.fill(incomeByMonth, BigDecimal.ZERO);
		Arrays.fill(expenseByMonth, BigDecimal.ZERO);

		for (MonthTotal m : incomeTotals) {
			Integer month = m.getMonth();
			if (month != null && month >= 1 && month <= 12) {
				incomeByMonth[month - 1] = m.getTotal() == null ? BigDecimal.ZERO : m.getTotal();
			}
		}
		for (MonthTotal m : expenseTotals) {
			Integer month = m.getMonth();
			if (month != null && month >= 1 && month <= 12) {
				expenseByMonth[month - 1] = m.getTotal() == null ? BigDecimal.ZERO : m.getTotal();
			}

		}

		// build month summaries and compute totals
		List<MonthSummary> months = IntStream.rangeClosed(1, 12)
				.mapToObj(i -> new MonthSummary(i, incomeByMonth[i - 1], expenseByMonth[i - 1]))
				.collect(Collectors.toList());

		BigDecimal totalIncome = Arrays.stream(incomeByMonth).reduce(BigDecimal.ZERO, BigDecimal::add);
		BigDecimal totalExpense = Arrays.stream(expenseByMonth).reduce(BigDecimal.ZERO, BigDecimal::add);
		BigDecimal balance = totalIncome.subtract(totalExpense);

		return new MonthlyReportResponse(userId, year, months, totalIncome, totalExpense, balance);
	}

	@Override
	@Transactional(readOnly = true)
	public YearlyReportResponse yearlyReport(Long userId, Integer startYear, Integer endYear) {
		if (startYear == null || endYear == null) {
			throw new IllegalArgumentException("startYear and endYear are required");
		}
		if (endYear < startYear) {
			throw new IllegalArgumentException("endYear must be >= startYear");
		}

		List<YearTotal> incomeTotals = transactionRepo.findYearlyTotals(userId, AmountType.INCOME, startYear, endYear);
		List<YearTotal> expenseTotals = transactionRepo.findYearlyTotals(userId, AmountType.EXPENSE, startYear,
				endYear);

		// Map year -> total
		Map<Integer, BigDecimal> incomeMap = incomeTotals.stream().filter(Objects::nonNull).collect(
				Collectors.toMap(YearTotal::getYear, y -> y.getTotal() == null ? BigDecimal.ZERO : y.getTotal()));

		Map<Integer, BigDecimal> expenseMap = expenseTotals.stream().filter(Objects::nonNull).collect(
				Collectors.toMap(YearTotal::getYear, y -> y.getTotal() == null ? BigDecimal.ZERO : y.getTotal()));

		List<YearSummary> years = new ArrayList<>();
		BigDecimal totalIncome = BigDecimal.ZERO;
		BigDecimal totalExpense = BigDecimal.ZERO;

		for (int y = startYear; y <= endYear; y++) {
			BigDecimal inc = incomeMap.getOrDefault(y, BigDecimal.ZERO);
			BigDecimal exp = expenseMap.getOrDefault(y, BigDecimal.ZERO);
			years.add(new YearSummary(y, inc, exp));
			totalIncome = totalIncome.add(inc);
			totalExpense = totalExpense.add(exp);
		}

		BigDecimal balance = totalIncome.subtract(totalExpense);
		return new YearlyReportResponse(userId, startYear, endYear, years, totalIncome, totalExpense, balance);
	}

	 @Override
	    @Transactional(readOnly = true)
	    public CategoryBreakdownResponse categoryBreakdown(Long userId, Integer year, Integer month) {
	        if (year == null || month == null) {
	            throw new IllegalArgumentException("year and month are required");
	        }
	        List<CategoryTotal> incomeCats = transactionRepo.findCategoryTotalsForMonth(userId, AmountType.INCOME, year, month);
	        List<CategoryTotal> expenseCats = transactionRepo.findCategoryTotalsForMonth(userId, AmountType.EXPENSE, year, month);

	        // combine both (usually you'll only want expense breakdown for expense chart)
	        // We'll return expense breakdown by default; if you want both, adapt DTO.
	        List<CategoryBreakdownItem> breakdown = expenseCats.stream()
	                .map(ct -> new CategoryBreakdownItem(ct.getCategoryId(), ct.getCategoryName(),
	                        ct.getTotal() == null ? BigDecimal.ZERO : ct.getTotal()))
	                .collect(Collectors.toList());

	        return new CategoryBreakdownResponse(userId, year, month, breakdown);
	    
	}

}
