package com.example.driveanalysis.cashlog.repository;

import com.example.driveanalysis.cashlog.entity.CashLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CashLogRepository extends JpaRepository<CashLog,Long> {
    List<CashLog> findAllByUserId(long userId);
}
