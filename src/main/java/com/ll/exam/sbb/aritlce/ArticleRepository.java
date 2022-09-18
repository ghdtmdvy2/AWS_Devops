package com.ll.exam.sbb.aritlce;

import com.ll.exam.sbb.base.RepositoryUtil;
import com.ll.exam.sbb.emotion.Emotion;
import com.ll.exam.sbb.question.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

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
