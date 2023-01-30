package com.example.driveanalysis.cashLog.service;

import com.example.driveanalysis.base.exception.DataNotFoundException;
import com.example.driveanalysis.cashLog.entity.CashLog;
import com.example.driveanalysis.cashLog.repository.CashLogRepository;
import com.example.driveanalysis.user.entity.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CashLogService {
    private final CashLogRepository cashLogRepository;

    public CashLog addCash(SiteUser user, long price, String eventType) {
        CashLog cashLog = new CashLog();
        cashLog.setUser(user);
        cashLog.setEventType(eventType);
        cashLog.setPrice(price);
        cashLogRepository.save(cashLog);
        return cashLog;
    }

    public CashLog getCashLog(CashLog cashLog) {
        return cashLogRepository.findById(cashLog.getId()).orElseThrow(() -> new DataNotFoundException("cashLog not found"));
    }
}
