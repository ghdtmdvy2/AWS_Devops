package com.ll.exam.sbb.service;

import com.ll.exam.sbb.aritlce.entity.Article;
import com.ll.exam.sbb.aritlce.service.ArticleService;
import com.ll.exam.sbb.user.entity.SiteUser;
import com.ll.exam.sbb.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Transactional
@ActiveProfiles("test")
class ArticleServiceTest {
    @Autowired
    ArticleService articleService;
    @Autowired
    UserService userService;
    @Test
    public void get_article_list(){
        Page<Article> articles = articleService.getList("", 0, "");
        assertThat(articles.getSize()).isGreaterThan(0);
    }
    @Test
    public void create_article(){
        SiteUser user = userService.getUser("user1");
        String subject = "제목1";
        String content = "내용1";
        Article article = articleService.create("제목1","내용1",user);
        assertThat(article).isNotNull();
        assertThat(article.getSubject()).isEqualTo(subject);
        assertThat(article.getContent()).isEqualTo(content);
        assertThat(article.getAuthor()).isEqualTo(user);
    }

    @Test
    public void get_article(){
        SiteUser user = userService.getUser("user1");
        String subject = "제목1";
        String content = "내용1";
        Article article = articleService.create(subject,content,user);
        Article findArticle = articleService.getArticle(article.getId());
        assertThat(findArticle).isNotNull();
        assertThat(findArticle.getSubject()).isEqualTo(subject);
        assertThat(findArticle.getContent()).isEqualTo(content);
        assertThat(findArticle.getAuthor()).isEqualTo(user);
    }
}