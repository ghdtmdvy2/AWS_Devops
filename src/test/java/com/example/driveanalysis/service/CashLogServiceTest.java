package com.example.driveanalysis.service;

import com.example.driveanalysis.cashlog.entity.CashLog;
import com.example.driveanalysis.cashlog.service.CashLogService;
import com.example.driveanalysis.user.entity.SiteUser;
import com.example.driveanalysis.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class CashLogServiceTest {
    @Autowired
    CashLogService cashLogService;

    @Autowired
    UserService userService;
    @Test
    void addCash(){
        SiteUser user = userService.getUser("user2");
        CashLog cashLog = cashLogService.addCash(user,100_000,"충전");
        List<CashLog> compareCashLogs = cashLogService.getCashLog(user.getId());
        CashLog compareCashLog = compareCashLogs.get(0);
        assertThat(cashLog.getUser()).isEqualTo(compareCashLog.getUser());
        assertThat(cashLog.getEventType()).isEqualTo(compareCashLog.getEventType());
        assertThat(cashLog.getId()).isEqualTo(compareCashLog.getId());
        assertThat(cashLog.getPrice()).isEqualTo(compareCashLog.getPrice());
    }
}