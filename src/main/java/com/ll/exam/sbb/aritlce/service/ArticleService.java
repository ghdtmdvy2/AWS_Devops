package com.ll.exam.sbb.aritlce.service;

import com.ll.exam.sbb.aritlce.entity.Article;
import com.ll.exam.sbb.aritlce.repository.ArticleRepository;
import com.ll.exam.sbb.base.exception.DataNotFoundException;
import com.ll.exam.sbb.user.entity.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;

    public Page<Article> getList(String kw, int page, String sortCode) {
        List<Sort.Order> sorts = new ArrayList<>();

        switch (sortCode) {
            case "OLD" -> sorts.add(Sort.Order.asc("id")); // 오래된순
            default -> sorts.add(Sort.Order.desc("id")); // 최신순
        }

        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts)); // 한 페이지에 10까지 가능

        if ( kw == null || kw.trim().length() == 0 || kw.equals("null") ) {
            return articleRepository.findAll(pageable);
        }

        return articleRepository.findDistinctBySubjectContainsOrContentContainsOrAuthor_usernameContainsOrAnswerList_contentContainsOrAnswerList_author_username(kw, kw, kw, kw, kw, pageable);
    }

    public Article getArticle(long id) {
        Optional<Article> o_article = articleRepository.findById(id);
        o_article.orElseThrow(() -> new DataNotFoundException("no %d question not found,".formatted(id)));
        Article article = o_article.get();
        article.setHitCount(article.getHitCount() + 1);
        articleRepository.save(article);
        return article;
    }

    public Article create(String subject, String content, SiteUser author) {
        Article a = new Article();
        a.setSubject(subject);
        a.setContent(content);
        a.setAuthor(author);
        a.setHitCount(0);
        a.setVoter(new HashSet<>());
        articleRepository.save(a);
        return a;
    }

    public void modify(Article article, String subject, String content) {
        article.setSubject(subject);
        article.setContent(content);
        articleRepository.save(article);
    }

    public void delete(Article article) {
        this.articleRepository.delete(article);
    }

    public void vote(Article article, SiteUser siteUser) {
        article.getVoter().add(siteUser);

        articleRepository.save(article);
    }
}
