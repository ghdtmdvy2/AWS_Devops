package com.example.driveanalysis.base.initData;

import com.example.driveanalysis.analysis.entity.Analysis;
import com.example.driveanalysis.answer.service.AnswerService;
import com.example.driveanalysis.aritlce.service.ArticleService;
import com.example.driveanalysis.cartitem.service.CartItemService;
import com.example.driveanalysis.emotion.service.EmotionService;
import com.example.driveanalysis.order.entity.ProductOrder;
import com.example.driveanalysis.order.service.ProductOrderService;
import com.example.driveanalysis.product.entity.Product;
import com.example.driveanalysis.user.entity.SiteUser;
import com.example.driveanalysis.user.service.UserService;
import com.example.driveanalysis.analysis.service.AnalysisService;
import com.example.driveanalysis.aritlce.entity.Article;
import com.example.driveanalysis.product.service.ProductService;

public interface InitDataBefore {
    default void before(UserService userService, ArticleService articleService, AnswerService answerService, AnalysisService analysisService,
                        EmotionService emotionService, ProductService productService, CartItemService cartItemService, ProductOrderService productOrderService) {
        SiteUser admin = userService.create("admin", "admin@test.com", "1234");
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

        boolean createConfirm = true;
        testEmotionsCreate(emotionService, analysis1, createConfirm);
        testEmotionsCreate(emotionService, analysis2, createConfirm);

        Product product1 = productService.create("감정 분석 기기", "감정을 분석 할 수 있는 기기입니다.", 10, user1, 150);
        Product product2 = productService.create("기기 성능 업그레이드", "기기 성능을 업그레이드 시켜서 더욱 더 감정을 세밀하게 분석할 수 있습니다.", 5, user1, 150);
        Product product3 = productService.create("제품 조립", "제품 조립을 한 상태로 드립니다.", 3, user2, 150);
        Product product4 = productService.create("제품 설치", "제품을 설치를 할 수 있습니다.", 1, user2, 150);

        cartItemService.addCartItem(product1,user1,1);
        cartItemService.addCartItem(product1,user1,1);
        cartItemService.addCartItem(product2,user1,1);
        cartItemService.addCartItem(product3,user1,1);

        ProductOrder fromCartProductOrder1 = productOrderService.createFromCartProductOrder(user1);
        productOrderService.payTossPayments(fromCartProductOrder1);

        cartItemService.addCartItem(product1,user1,1);
        cartItemService.addCartItem(product1,user1,1);
        cartItemService.addCartItem(product2,user1,1);
        cartItemService.addCartItem(product3,user1,1);

        ProductOrder fromCartProductOrder2 = productOrderService.createFromCartProductOrder(user1);
        productOrderService.payTossPayments(fromCartProductOrder2);

        cartItemService.addCartItem(product1,user3,1);
        cartItemService.addCartItem(product1,user3,1);
        cartItemService.addCartItem(product2,user3,1);
        cartItemService.addCartItem(product3,user3,1);

        cartItemService.addCartItem(product1,user1,1);
        cartItemService.addCartItem(product1,user1,1);
        cartItemService.addCartItem(product2,user1,1);
        cartItemService.addCartItem(product3,user1,1);

        cartItemService.addCartItem(product1,user2,1);
        cartItemService.addCartItem(product1,user2,1);
        cartItemService.addCartItem(product2,user2,1);
        cartItemService.addCartItem(product3,user2,1);
    }

    private void testEmotionsCreate(EmotionService emotionService, Analysis analysis, boolean createConfirm) {
        int sec = 0;
        int min = 0;
        String createDate;
        if (createConfirm) {
            for (int i = 0; i<3; i++){
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
                emotionService.testDataEmotionCreate(analysis,angry,happy,neutral,createDate);
            }
        }
    }
}
