package com.example.driveanalysis.service;

import com.example.driveanalysis.cashLog.entity.CashLog;
import com.example.driveanalysis.cashLog.repository.CashLogRepository;
import com.example.driveanalysis.cashLog.service.CashLogService;
import com.example.driveanalysis.user.entity.SiteUser;
import com.example.driveanalysis.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class CashLogServiceTest {
    @Autowired
    CashLogService cashLogService;

    @Autowired
    UserService userService;
    @Test
    public void addCash(){
        SiteUser user = userService.getUser("user1");
        CashLog cashLog = cashLogService.addCash(user,100_000,"충전");
        List<CashLog> compareCashLogs = cashLogService.getCashLog(user.getId());
        CashLog compareCashLog = compareCashLogs.get(0);
        assertThat(cashLog.getUser()).isEqualTo(compareCashLog.getUser());
        assertThat(cashLog.getEventType()).isEqualTo(compareCashLog.getEventType());
        assertThat(cashLog.getId()).isEqualTo(compareCashLog.getId());
        assertThat(cashLog.getPrice()).isEqualTo(compareCashLog.getPrice());
    }
}