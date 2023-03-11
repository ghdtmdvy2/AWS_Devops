package com.example.driveanalysis.userhistory.repository;

import com.example.driveanalysis.userhistory.entity.UserHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserHistoryRepository extends JpaRepository<UserHistory,Long> {
}
