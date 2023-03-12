package com.example.driveanalysis.userhistory.service;


import com.example.driveanalysis.user.entity.SiteUser;
import com.example.driveanalysis.userhistory.entity.UserHistory;
import com.example.driveanalysis.userhistory.repository.UserHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserHistoryService {
    private final UserHistoryRepository userHistoryRepository;
    public UserHistory create(SiteUser siteUser) {
        UserHistory userHistory = new UserHistory();
        userHistory.setId(siteUser.getId());
        userHistory.setEmail(siteUser.getEmail());
        userHistory.setPassword(siteUser.getPassword());
        userHistory.setProductPaid(siteUser.isProductPaid());
        userHistory.setUsername(siteUser.getUsername());
        userHistoryRepository.save(userHistory);
        return userHistory;
    }
}
