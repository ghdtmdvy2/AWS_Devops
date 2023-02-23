package com.example.driveanalysis.aritlce.repository;

import com.example.driveanalysis.aritlce.entity.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.Entity;
import java.util.Optional;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    Page<Article> findDistinctBySubjectContainsOrContentContainsOrAuthor_usernameContainsOrAnswerList_contentContainsOrAnswerList_author_username(String kw, String kw_, String kw__, String kw___, String kw____, Pageable pageable);

    @Override
    @EntityGraph(attributePaths = "answerList")
    @Query(value = "select a from Article a join fetch a.author where a.id = :analysisId")
    Optional<Article> findById(@Param(value = "analysisId") Long analysisId);

}
