package com.pfm.controller;

import com.pfm.dto.CategoryBreakdownResponse;
import com.pfm.dto.MonthlyReportResponse;
import com.pfm.dto.YearlyReportResponse;
import com.pfm.security.CustomUserDetails;
import com.pfm.service.ReportService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

	private final ReportService reportService;

	public ReportController(ReportService reportService) {
		this.reportService = reportService;
	}

	@GetMapping("/monthly")
	public ResponseEntity<MonthlyReportResponse> monthly(@AuthenticationPrincipal CustomUserDetails principal,
			@RequestParam Integer year) {
		MonthlyReportResponse resp = reportService.monthlyReport(principal.getUserId(), year);
		return ResponseEntity.ok(resp);
	}

	@GetMapping("/yearly")
	public ResponseEntity<YearlyReportResponse> yearly(@AuthenticationPrincipal CustomUserDetails principal,
			@RequestParam Integer startYear, @RequestParam Integer endYear) {
		YearlyReportResponse resp = reportService.yearlyReport(principal.getUserId(), startYear, endYear);
		return ResponseEntity.ok(resp);
	}

	@GetMapping("/monthly-by-category")
	public ResponseEntity<CategoryBreakdownResponse> monthlyByCategory(
			@AuthenticationPrincipal CustomUserDetails principal, @RequestParam Integer year,
			@RequestParam Integer month) {
		CategoryBreakdownResponse resp = reportService.categoryBreakdown(principal.getUserId(), year, month);
		return ResponseEntity.ok(resp);
	}
}
