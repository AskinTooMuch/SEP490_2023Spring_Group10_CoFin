package com.example.eims.repository;

import com.example.eims.entity.SalaryHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalaryHistoryRepository extends JpaRepository<SalaryHistory,Long> {
}
