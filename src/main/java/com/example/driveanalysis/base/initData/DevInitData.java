package com.example.driveanalysis.base.initData;

import com.example.driveanalysis.answer.service.AnswerService;
import com.example.driveanalysis.aritlce.service.ArticleService;
import com.example.driveanalysis.cartitem.service.CartItemService;
import com.example.driveanalysis.emotion.service.EmotionService;
import com.example.driveanalysis.user.service.UserService;
import com.example.driveanalysis.analysis.service.AnalysisService;
import com.example.driveanalysis.product.service.ProductService;
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
            ProductService productService,
            CartItemService cartItemService) {
        return args -> {
            before(userService, articleService, answerService, analysisService, emotionService, productService,cartItemService);
        };
    }


}
