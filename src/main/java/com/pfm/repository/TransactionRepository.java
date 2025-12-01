package com.pfm.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pfm.enums.AmountType;
import com.pfm.model.Transaction;
import com.pfm.projections.CategoryTotal;
import com.pfm.projections.MonthTotal;
import com.pfm.projections.YearTotal;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    // use nested property user.userId -> method name: findByUser_UserId(...)
    Page<Transaction> findByUser_UserId(Long userId, Pageable pageable);

    List<Transaction> findByUser_UserIdAndDateBetween(Long userId, LocalDateTime start, LocalDateTime end);

    List<Transaction> findByUser_UserIdAndCategory_categoryId(Long userId, Long categoryId);

    Optional<Transaction> findByTransactionIdAndUser_UserId(Long id, Long userId);

    @Query("SELECT FUNCTION('MONTH', t.date) AS month, COALESCE(SUM(t.amount),0) AS total " +
           "FROM Transaction t " +
           "WHERE t.user.userId = :userId AND t.type = :type AND FUNCTION('YEAR', t.date) = :year " +
           "GROUP BY FUNCTION('MONTH', t.date) ORDER BY FUNCTION('MONTH', t.date)")
    List<MonthTotal> findMonthlyTotalsForYear(@Param("userId") Long userId,
                                              @Param("type") AmountType type,
                                              @Param("year") Integer year);

    @Query("SELECT FUNCTION('YEAR', t.date) AS year, COALESCE(SUM(t.amount),0) AS total " +
           "FROM Transaction t " +
           "WHERE t.user.userId = :userId AND t.type = :type AND FUNCTION('YEAR', t.date) BETWEEN :start AND :end " +
           "GROUP BY FUNCTION('YEAR', t.date) ORDER BY FUNCTION('YEAR', t.date)")
    List<YearTotal> findYearlyTotals(@Param("userId") Long userId,
                                     @Param("type") AmountType type,
                                     @Param("start") Integer start,
                                     @Param("end") Integer end);

    @Query("SELECT t.category.categoryId AS categoryId, t.category.categoryName AS categoryName, COALESCE(SUM(t.amount),0) AS total " +
           "FROM Transaction t " +
           "WHERE t.user.userId = :userId AND t.type = :type AND FUNCTION('YEAR', t.date) = :year AND FUNCTION('MONTH', t.date) = :month " +
           "GROUP BY t.category.categoryId, t.category.categoryName")
    List<CategoryTotal> findCategoryTotalsForMonth(@Param("userId") Long userId,
                                                   @Param("type") AmountType type,
                                                   @Param("year") Integer year,
                                                   @Param("month") Integer month);

}
