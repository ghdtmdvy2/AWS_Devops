package com.ll.exam.sbb.aritlce.repository;

import com.ll.exam.sbb.aritlce.entity.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    Article findBySubject(String subject);

    Article findBySubjectAndContent(String subject, String content);

    List<Article> findBySubjectLike(String s);

    Page<Article> findBySubjectContains(String kw, Pageable pageable);

    Page<Article> findBySubjectContainsOrContentContains(String kw, String kw_, Pageable pageable);

    Page<Article> findBySubjectContainsOrContentContainsOrAuthor_usernameContains(String kw, String kw_, String kw__, Pageable pageable);

    Page<Article> findBySubjectContainsOrContentContainsOrAuthor_usernameContainsOrAnswerList_contentContains(String kw, String kw_, String kw__, String kw___, Pageable pageable);

    Page<Article> findDistinctBySubjectContainsOrContentContainsOrAuthor_usernameContainsOrAnswerList_contentContains(String kw, String kw_, String kw__, String kw___, Pageable pageable);

    Page<Article> findDistinctBySubjectContainsOrContentContainsOrAuthor_usernameContainsOrAnswerList_contentContainsOrAnswerList_author_username(String kw, String kw_, String kw__, String kw___, String kw____, Pageable pageable);


}
