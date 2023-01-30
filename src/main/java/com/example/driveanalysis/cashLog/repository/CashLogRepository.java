package com.example.driveanalysis.cashLog.repository;

import com.example.driveanalysis.cashLog.entity.CashLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CashLogRepository extends JpaRepository<CashLog,Long> {
}
