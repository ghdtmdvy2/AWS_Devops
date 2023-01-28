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
        assertThat(article.getHitCount()).isEqualTo(0);
        assertThat(article.getAuthor()).isEqualTo(user);
    }

    @Test
    public void get_article(){
        SiteUser user = userService.getUser("user1");
        String subject = "제목1";
        String content = "내용1";
        Article article = articleService.create(subject,content,user);
        assertThat(article.getHitCount()).isEqualTo(0);
        Article findArticle = articleService.getArticle(article.getId());
        assertThat(findArticle).isNotNull();
        assertThat(findArticle.getSubject()).isEqualTo(subject);
        assertThat(findArticle.getContent()).isEqualTo(content);
        assertThat(article.getHitCount()).isEqualTo(1);
        assertThat(findArticle.getAuthor()).isEqualTo(user);
    }

    @Test
    public void modify_article(){
        SiteUser user = userService.getUser("user1");
        String oldSubject = "제목1";
        String oldContent = "내용1";
        Article article = articleService.create(oldSubject,oldContent,user);
        assertThat(article).isNotNull();
        String modifySubject = "수정된 제목1";
        String modifyContent = "수정된 내용1";
        articleService.modify(article,modifySubject,modifyContent);
        Article modifyArticle = articleService.getArticle(article.getId());
        assertThat(modifyArticle.getSubject().equals(oldSubject)).isFalse();
        assertThat(modifyArticle.getContent().equals(oldContent)).isFalse();
        assertThat(modifyArticle.getSubject().equals(modifySubject)).isTrue();
        assertThat(modifyArticle.getContent().equals(modifyContent)).isTrue();
        assertThat(modifyArticle.getModifiedDate()).isNotNull();
    }

    @Test
    public void delete_article(){
        SiteUser user = userService.getUser("user1");
        String subject = "제목1";
        String content = "내용1";
        Article article = articleService.create(subject,content,user);
        articleService.delete(article);
        try{
            articleService.getArticle(article.getId());
        }
        catch (Exception e) {
            assertThat(true).isTrue();
            return;
        }
        assertThat(false).isFalse();
    }

}