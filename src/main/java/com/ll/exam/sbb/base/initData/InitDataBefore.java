package com.ll.exam.sbb.base.initData;

import com.ll.exam.sbb.analysis.service.AnalysisService;
import com.ll.exam.sbb.answer.service.AnswerService;
import com.ll.exam.sbb.aritlce.service.ArticleService;
import com.ll.exam.sbb.emotion.service.EmotionService;
import com.ll.exam.sbb.user.entity.SiteUser;
import com.ll.exam.sbb.user.service.UserService;

public interface InitDataBefore {
    default void before(UserService userService, ArticleService articleService, AnswerService answerService, AnalysisService analysisService, EmotionService emotionService) {
        SiteUser user1 = userService.create("user1", "user1@test.com", "1234");
        SiteUser user2 = userService.create("user2", "user2@test.com", "1234");
    }
}
