package com.example.driveanalysis.service;

import com.example.driveanalysis.aritlce.entity.Article;
import com.example.driveanalysis.aritlce.service.ArticleService;
import com.example.driveanalysis.user.entity.SiteUser;
import com.example.driveanalysis.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class ArticleServiceTest {
    @Autowired
    ArticleService articleService;
    @Autowired
    UserService userService;
    @Test
    void get_article_list(){
        Page<Article> articles = articleService.getList("", 0, "");
        assertThat(articles.getSize()).isPositive();
    }
    @Test
    void create_article(){
        SiteUser user = userService.getUser("user1");
        String subject = "제목1";
        String content = "내용1";
        Article article = articleService.create("제목1","내용1",user);
        assertThat(article).isNotNull();
        assertThat(article.getSubject()).isEqualTo(subject);
        assertThat(article.getContent()).isEqualTo(content);
        assertThat(article.getHitCount()).isZero();
        assertThat(article.getVoter()).isEmpty();
        assertThat(article.getAuthor()).isEqualTo(user);
    }

    @Test
    void get_article(){
        SiteUser user = userService.getUser("user1");
        String subject = "제목1";
        String content = "내용1";
        Article article = articleService.create(subject,content,user);
        assertThat(article.getHitCount()).isZero();
        Article findArticle = articleService.getArticle(article.getId());
        assertThat(findArticle).isEqualTo(article);
    }

    @Test
    void modify_article(){
        SiteUser user = userService.getUser("user1");
        String oldSubject = "제목1";
        String oldContent = "내용1";
        Article article = articleService.create(oldSubject,oldContent,user);
        assertThat(article).isNotNull();
        String modifySubject = "수정된 제목1";
        String modifyContent = "수정된 내용1";
        articleService.modify(article,modifySubject,modifyContent);
        Article modifyArticle = articleService.getArticle(article.getId());
        assertThat(modifyArticle.getSubject()).isNotEqualTo(oldSubject);
        assertThat(modifyArticle.getContent()).isNotEqualTo(oldContent);
        assertThat(modifyArticle.getSubject()).isEqualTo(modifySubject);
        assertThat(modifyArticle.getContent()).isEqualTo(modifyContent);
        assertThat(modifyArticle.getModifiedDate()).isNotNull();
    }

    @Test
    void delete_article(){
        SiteUser user = userService.getUser("user1");
        String subject = "제목1";
        String content = "내용1";
        Article article = articleService.create(subject,content,user);
        articleService.delete(article);
        Article deleteArticle = null;
        try{
            deleteArticle = articleService.getArticle(article.getId());
        }
        catch (Exception e) {
        }
        assertThat(deleteArticle).isNull();
    }

    @Test
    void increase_article_voter(){
        SiteUser user1 = userService.getUser("user1");
        SiteUser user2 = userService.getUser("user2");
        Article article = articleService.getArticle(4);
        assertThat(article.getVoter()).isEmpty();
        articleService.vote(article,user1);
        assertThat(article.getVoter()).hasSize(1);
        articleService.vote(article,user2);
        assertThat(article.getVoter()).hasSize(2);
        articleService.vote(article,user2);
        assertThat(article.getVoter()).hasSize(2);
    }

    @Test
    void increase_article_hit_count(){
        SiteUser user1 = userService.getUser("user1");
        String subject = "제목1";
        String content = "내용1";
        Article article = articleService.create(subject,content,user1);
        assertThat(article.getSubject()).isEqualTo(subject);
        assertThat(article.getContent()).isEqualTo(content);
        assertThat(article.getHitCount()).isZero();
        assertThat(article.getVoter()).isEmpty();
        articleService.articleIncreaseHitCount(article);
        assertThat(article.getHitCount()).isEqualTo(1);
    }
}