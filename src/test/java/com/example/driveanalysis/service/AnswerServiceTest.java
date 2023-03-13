package com.example.driveanalysis.service;

import com.example.driveanalysis.answer.entity.Answer;
import com.example.driveanalysis.answer.service.AnswerService;
import com.example.driveanalysis.aritlce.entity.Article;
import com.example.driveanalysis.aritlce.service.ArticleService;
import com.example.driveanalysis.user.entity.SiteUser;
import com.example.driveanalysis.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class AnswerServiceTest {
    @Autowired
    AnswerService answerService;
    @Autowired
    ArticleService articleService;
    @Autowired
    UserService userService;
    SiteUser user1 = new SiteUser();
    SiteUser user2 = new SiteUser();
    SiteUser user3 = new SiteUser();
    Article article = new Article();
    @BeforeEach
    void beforeEach(){
        user1 = userService.getUser("user1");
        user2 = userService.getUser("user2");
        user3 = userService.getUser("user3");
        article = articleService.getArticle(3L);
    }
    @Test
    void get_answers(){
        List<Answer> userAnswers = answerService.getAnswers(user1.getId());
        assertThat(userAnswers).hasSize(2);
    }
    @Test
    void get_answer(){
        Answer answer = answerService.getAnswer(2L);
        assertThat(answer).isNotNull();
    }
    @Test
    void create(){
        answerService.create(article, "테스트 댓글1", user2);
        answerService.create(article, "테스트 댓글2", user2);
        List<Answer> answers = answerService.getAnswers(user2.getId());
        assertThat(answers).hasSize(2);
    }
    @Test
    void modify(){
        Answer originAnswer = answerService.create(article, "테스트 댓글1", user2);
        String modifyContent = "수정 테스트 댓글1";
        answerService.modify(originAnswer,modifyContent);
        Answer modifyAnswer = answerService.getAnswer(originAnswer.getId());
        assertThat(modifyAnswer.getContent()).isEqualTo(modifyContent);
    }
    @Test
    void delete(){
        List<Answer> user1Answers1 = answerService.getAnswers(user3.getId());
        assertThat(user1Answers1).hasSize(0);
        Answer answer1 = answerService.create(article, "테스트 댓글1", user3);
        Answer answer2 = answerService.create(article, "테스트 댓글2", user3);
        List<Answer> user1Answers2 = answerService.getAnswers(user3.getId());
        assertThat(user1Answers2).hasSize(2);
        answerService.delete(answer1);
        answerService.delete(answer2);
        List<Answer> user1Answers3 = answerService.getAnswers(user3.getId());
        assertThat(user1Answers3).hasSize(0);
    }
}