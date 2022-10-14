package com.ll.exam.sbb.analysis.repository;

import com.ll.exam.sbb.analysis.entity.Analysis;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface AnalysisRepository extends JpaRepository<Analysis, Long> {
    Analysis findBySubject(String subject);

    Analysis findBySubjectAndContent(String subject, String content);

    List<Analysis> findBySubjectLike(String s);

    @Transactional
    @Modifying
    @Query(value = "ALTER TABLE analysis AUTO_INCREMENT = 1", nativeQuery = true)
    void truncate(); // 이거 지우면 안됨, truncateTable 하면 자동으로 이게 실행됨

    Page<Analysis> findDistinctBySubjectContainsOrContentContainsOrAuthor_usernameContainsOrAuthor_Id(String kw, String kw_, String kw__, long id, Pageable pageable);

    Page<Analysis> findAllByAuthor_Id(Pageable pageable, long id);
}
