package com.pfm.service;

import org.springframework.stereotype.Service;

import com.pfm.dto.CategoryBreakdownResponse;
import com.pfm.dto.MonthlyReportResponse;
import com.pfm.dto.YearlyReportResponse;

@Service
public interface ReportService {

	MonthlyReportResponse monthlyReport(Long userId, Integer year);

	YearlyReportResponse yearlyReport(Long userId, Integer startYear, Integer endYear);

	CategoryBreakdownResponse categoryBreakdown(Long userId, Integer year, Integer month);

}
