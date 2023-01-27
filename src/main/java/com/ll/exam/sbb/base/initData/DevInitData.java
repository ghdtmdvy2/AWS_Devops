package com.ll.exam.sbb.base.initData;

import com.ll.exam.sbb.analysis.service.AnalysisService;
import com.ll.exam.sbb.answer.service.AnswerService;
import com.ll.exam.sbb.aritlce.service.ArticleService;
import com.ll.exam.sbb.emotion.service.EmotionService;
import com.ll.exam.sbb.product.service.ProductService;
import com.ll.exam.sbb.user.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("dev")
public class DevInitData implements InitDataBefore {
    @Bean
    CommandLineRunner initData(
            UserService userService,
            ArticleService articleService,
            AnswerService answerService,
            AnalysisService analysisService,
            EmotionService emotionService,
            ProductService productService) {
        return args -> {
            before(userService, articleService, answerService, analysisService, emotionService, productService);
        };
    }


}
