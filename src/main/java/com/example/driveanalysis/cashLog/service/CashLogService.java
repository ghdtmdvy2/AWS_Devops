package com.example.driveanalysis.cashLog.service;

import com.example.driveanalysis.base.exception.DataNotFoundException;
import com.example.driveanalysis.cashLog.entity.CashLog;
import com.example.driveanalysis.cashLog.repository.CashLogRepository;
import com.example.driveanalysis.user.entity.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<CashLog> getCashLog(long userId) {
        return cashLogRepository.findAllByUserId(userId);
    }
}
