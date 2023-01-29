package com.example.driveanalysis.base.initData;

import com.example.driveanalysis.analysis.entity.Analysis;
import com.example.driveanalysis.answer.service.AnswerService;
import com.example.driveanalysis.aritlce.service.ArticleService;
import com.example.driveanalysis.emotion.service.EmotionService;
import com.example.driveanalysis.user.entity.SiteUser;
import com.example.driveanalysis.user.service.UserService;
import com.example.driveanalysis.analysis.service.AnalysisService;
import com.example.driveanalysis.aritlce.entity.Article;
import com.example.driveanalysis.product.service.ProductService;

public interface InitDataBefore {
    default void before(UserService userService, ArticleService articleService, AnswerService answerService, AnalysisService analysisService, EmotionService emotionService, ProductService productService) {
        SiteUser user1 = userService.create("user1", "user1@test.com", "1234");
        SiteUser user2 = userService.create("user2", "user2@test.com", "1234");
        SiteUser user3 = userService.create("user3", "user3@test.com", "1234");

        Article article1 = articleService.create("제목1", "내용1", user1);
        Article article2 = articleService.create("제목2","내용2", user1);
        Article article3 = articleService.create("제목3","내용3", user1);
        Article article4 = articleService.create("제목4","내용4", user1);

        answerService.create(article1,"댓글 내용1",user1);

        articleService.vote(article2,user1);

        Analysis analysis1 = analysisService.create(user1);
        Analysis analysis2 = analysisService.create(user2);

        testEmotionsCreate(emotionService, analysis1);
        testEmotionsCreate(emotionService, analysis2);

        productService.create("user1 상품1", "user1 상품 내용1", 100_000, user1);
        productService.create("user1 상품2", "user1 상품 내용2", 150_000, user1);
        productService.create("user2 상품1", "user2 상품 내용1", 200_000, user2);
        productService.create("user2 상품2", "user2 상품 내용2", 250_000, user2);
    }

    private void testEmotionsCreate(EmotionService emotionService, Analysis analysis1) {
        int sec = 0;
        int min = 0;
        String createDate;
        for (int i = 0; i<100; i++){
            sec++;
            if (sec == 60) {
                min++;
                sec = 0;
            }
            createDate = "2023-01-01 00:%02d:%02d.000000".formatted(min,sec);
            double angryRatio = Math.random();
            double angry = 100 * angryRatio;
            double happy = 100 * ((1 - angryRatio) * 0.25);
            double neutral = 100 * ((1 - angryRatio) * 0.75);
            emotionService.testDataEmotionCreate(analysis1,angry,happy,neutral,createDate);
        }
    }
}