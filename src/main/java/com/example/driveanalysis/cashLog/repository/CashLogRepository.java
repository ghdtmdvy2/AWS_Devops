package com.example.driveanalysis.cashLog.repository;

import com.example.driveanalysis.cashLog.entity.CashLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CashLogRepository extends JpaRepository<CashLog,Long> {
    List<CashLog> findAllByUserId(long userId);
}
