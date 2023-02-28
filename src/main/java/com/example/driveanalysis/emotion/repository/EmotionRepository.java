package com.example.driveanalysis.emotion.repository;

import com.example.driveanalysis.base.util.RepositoryUtil;
import com.example.driveanalysis.emotion.entity.Emotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

public interface EmotionRepository extends JpaRepository<Emotion, Long>, RepositoryUtil {

    List<Emotion> findAllByCreatedDateBetweenAndAuthorId(@Param("fromDate") LocalDateTime fromDate, @Param("toDate")LocalDateTime toDate, @Param("authorId")Long authorId);

    List<Emotion> findAllByCreatedDateBetween(@Param("fromDate")LocalDateTime fromDate,@Param("toDate") LocalDateTime toDate);

    @Transactional
    @Modifying
    @Query(value = "ALTER TABLE emotion AUTO_INCREMENT = 1", nativeQuery = true)
    void truncate(); // 이거 지우면 안됨, truncateTable 하면 자동으로 이게 실행됨

    List<Emotion> findAllByAuthorId(@Param("authorId")Long authorId);
}
