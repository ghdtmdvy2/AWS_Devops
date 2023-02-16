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

//    @Query("select e from Emotion as e join fetch SiteUser as su on su.id = e.author.id join fetch Analysis as a on a.author.id = su.id where e.createdDate <= :fromDate and e.createdDate <= :toDate and e.author.id = :authorId ")
    List<Emotion> findAllByCreatedDateBetweenAndAuthor_id(@Param("fromDate") LocalDateTime fromDate, @Param("toDate")LocalDateTime toDate, @Param("authorId")Long authorId);

//    @Query("select e from Emotion as e join fetch SiteUser as su on su.id = e.author.id join fetch Analysis as a on a.author.id = su.id where e.createdDate <= :fromDate and e.createdDate <= :toDate")
    List<Emotion> findAllByCreatedDateBetween(@Param("fromDate")LocalDateTime fromDate,@Param("toDate") LocalDateTime toDate);

    @Transactional
    @Modifying
    @Query(value = "ALTER TABLE emotion AUTO_INCREMENT = 1", nativeQuery = true)
    void truncate(); // 이거 지우면 안됨, truncateTable 하면 자동으로 이게 실행됨

//    @Query("select e from Emotion as e join fetch SiteUser as su on su.id = e.author.id join fetch Analysis as a on a.author.id = su.id where e.author.id = :authorId")
    List<Emotion> findAllByAuthor_id(@Param("authorId")Long authorId);
}
