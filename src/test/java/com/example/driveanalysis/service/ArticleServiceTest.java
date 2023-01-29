package com.example.driveanalysis.service;

import com.example.driveanalysis.aritlce.service.ArticleService;
import com.example.driveanalysis.user.entity.SiteUser;
import com.example.driveanalysis.user.service.UserService;
import com.example.driveanalysis.aritlce.entity.Article;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

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
        assertThat(article.getVoter().size()).isEqualTo(0);
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
        assertThat(article.getHitCount()).isEqualTo(0);
        assertThat(article.getVoter().size()).isEqualTo(0);
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
    @Test
    public void increase_article_voter(){
        SiteUser user1 = userService.getUser("user1");
        SiteUser user2 = userService.getUser("user2");
        String subject = "제목1";
        String content = "내용1";
        Article article = articleService.create(subject,content,user1);
        assertThat(article.getSubject()).isEqualTo(subject);
        assertThat(article.getContent()).isEqualTo(content);
        assertThat(article.getHitCount()).isEqualTo(0);
        assertThat(article.getVoter().size()).isEqualTo(0);
        articleService.vote(article,user1);
        assertThat(article.getVoter().size()).isEqualTo(1);
        articleService.vote(article,user2);
        assertThat(article.getVoter().size()).isEqualTo(2);
        articleService.vote(article,user2);
        assertThat(article.getVoter().size()).isEqualTo(2);
    }

    @Test
    public void increase_article_hit_count(){
        SiteUser user1 = userService.getUser("user1");
        String subject = "제목1";
        String content = "내용1";
        Article article = articleService.create(subject,content,user1);
        assertThat(article.getSubject()).isEqualTo(subject);
        assertThat(article.getContent()).isEqualTo(content);
        assertThat(article.getHitCount()).isEqualTo(0);
        assertThat(article.getVoter().size()).isEqualTo(0);
        articleService.articleIncreaseHitCount(article);
        article = articleService.getArticle(article.getId());
        assertThat(article.getHitCount()).isEqualTo(1);
    }
}