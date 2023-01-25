package com.ll.exam.sbb.base.initData;

import com.ll.exam.sbb.analysis.entity.Analysis;
import com.ll.exam.sbb.analysis.service.AnalysisService;
import com.ll.exam.sbb.answer.service.AnswerService;
import com.ll.exam.sbb.aritlce.entity.Article;
import com.ll.exam.sbb.aritlce.service.ArticleService;
import com.ll.exam.sbb.emotion.service.EmotionService;
import com.ll.exam.sbb.user.entity.SiteUser;
import com.ll.exam.sbb.user.service.UserService;

public interface InitDataBefore {
    default void before(UserService userService, ArticleService articleService, AnswerService answerService, AnalysisService analysisService, EmotionService emotionService) {
        SiteUser user1 = userService.create("user1", "user1@test.com", "1234");
        SiteUser user2 = userService.create("user2", "user2@test.com", "1234");

        Article article1 = articleService.create("제목1", "내용1", user1);
        Article article2 = articleService.create("제목2","내용2", user1);
        Article article3 = articleService.create("제목3","내용3", user1);
        Article article4 = articleService.create("제목4","내용4", user1);

        answerService.create(article1,"댓글 내용1",user1);

        Analysis analysis1 = analysisService.create("user1의 감정 기록 제목1", "user1의 감정 기록 내용1", user1);
        Analysis analysis2 = analysisService.create("user2의 감정 기록 제목1", "user2의 감정 기록 내용1", user2);

        testEmotionsCreate(emotionService, analysis1);
        testEmotionsCreate(emotionService, analysis2);
    }

    private void testEmotionsCreate(EmotionService emotionService, Analysis analysis1) {
        for (int i = 0; i<100; i++){
            double angryRatio = Math.random();
            double angry = 100 * angryRatio;
            double happy = 100 * ((1 - angryRatio) * 0.25);
            double neutral = 100 * ((1 - angryRatio) * 0.75);
            emotionService.emotionCreate(analysis1,angry,happy,neutral);
        }
    }
}
