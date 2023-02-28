package com.example.driveanalysis.user.repository;

import com.example.driveanalysis.base.util.RepositoryUtil;
import com.example.driveanalysis.user.entity.SiteUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface UserRepository extends JpaRepository<SiteUser, Long>, RepositoryUtil {
    boolean existsByUsername(String username);

    Optional<SiteUser> findByUsername(String username);



    @Transactional
    @Modifying
    @Query(value = "ALTER TABLE site_user AUTO_INCREMENT = 1", nativeQuery = true)
    void truncate(); // 이거 지우면 안됨, truncateTable 하면 자동으로 이게 실행됨
}
