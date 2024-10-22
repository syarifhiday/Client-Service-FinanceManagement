package com.syarif.financemanagement.financemanagement.repository;

import com.syarif.financemanagement.financemanagement.entity.Budget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BudgetRepository extends JpaRepository<Budget, String> {
    @Query("SELECT b FROM Budget b WHERE b.user.id = :userId")
    List<Budget> findAllByUserId(@Param("userId") String userId);

}
