package com.example.driveanalysis.listeners;

import com.example.driveanalysis.base.util.BeanUtils;
import com.example.driveanalysis.user.entity.SiteUser;
import com.example.driveanalysis.user.repository.UserRepository;
import com.example.driveanalysis.userhistory.entity.UserHistory;
import com.example.driveanalysis.userhistory.repository.UserHistoryRepository;
import com.example.driveanalysis.userhistory.service.UserHistoryService;
import org.springframework.stereotype.Component;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

@Component
public class UserHistoryEntityListeners {

    @PrePersist
    @PreUpdate
    public void userHistoryInsertAndUpdate(Object o){
        UserHistoryRepository userHistoryRepository = BeanUtils.getBean(UserHistoryRepository.class);
        SiteUser siteUser = (SiteUser) o;
        UserHistory userHistory = new UserHistory();
        userHistory.setId(siteUser.getId());
        userHistory.setEmail(siteUser.getEmail());
        userHistory.setPassword(siteUser.getPassword());
        userHistory.setProductPaid(siteUser.isProductPaid());
        userHistory.setUsername(siteUser.getUsername());
        userHistoryRepository.save(userHistory);
    }
}
