package com.example.driveanalysis.service;

import com.example.driveanalysis.analysis.entity.Analysis;
import com.example.driveanalysis.analysis.service.AnalysisService;
import com.example.driveanalysis.base.util.Ut;
import com.example.driveanalysis.emotion.service.EmotionService;
import com.example.driveanalysis.user.entity.SiteUser;
import com.example.driveanalysis.user.service.UserService;
import com.example.driveanalysis.emotion.entity.Emotion;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class EmotionServiceTest {
    @Autowired
    UserService userService;
    @Autowired
    EmotionService emotionService;

    @Autowired
    AnalysisService analysisService;
    @Test
    public void current_user_emotion_find(){
        SiteUser user1 = userService.getUser("user1");
        SiteUser user2 = userService.getUser("user2");

        List<Emotion> emotionsUser1 = emotionService.currentUserFindEmotions(user1.getId(), "");
        assertThat(emotionsUser1.size()).isGreaterThan(0);

        List<Emotion> emotionsUser2 = emotionService.currentUserFindEmotions(user2.getId(), "");
        assertThat(emotionsUser2.size()).isGreaterThan(0);
    }
    @Test
    public void entire_user_emotion_find(){
        List<Emotion> emotions = emotionService.AllUsersFindEmotions("");
        assertThat(emotions.size()).isGreaterThan(0);
    }
    @Test
    public void difference_emotion_between_current_user_entire_user_not_same(){
        SiteUser user1 = userService.getUser("user1");
        List<Emotion> emotionsUser1 = emotionService.currentUserFindEmotions(user1.getId(),"");
        assertThat(emotionsUser1.size()).isGreaterThan(0);
        List<Emotion> emotionsEntireEmotion = emotionService.AllUsersFindEmotions("");
        assertThat(emotionsEntireEmotion.size()).isGreaterThan(0);

        double[] emotionsAvgUser1 = Ut.emotionsAvgCreate(emotionsUser1);
        double[] emotionsAvgEntireUser = Ut.emotionsAvgCreate(emotionsEntireEmotion);

        assertThat(emotionsAvgEntireUser[0] == emotionsAvgUser1[0]).isFalse();
        assertThat(emotionsAvgEntireUser[1] == emotionsAvgUser1[1]).isFalse();
        assertThat(emotionsAvgEntireUser[2] == emotionsAvgUser1[2]).isFalse();
    }

    @Test
    public void emotion_create(){
        SiteUser user = userService.getUser("user3");
        Analysis analysis = analysisService.create(user);
        emotionService.emotionCreate(analysis,30,50,20);
        emotionService.emotionCreate(analysis,30,50,20);
        emotionService.emotionCreate(analysis,30,50,20);
        List<Emotion> emotions = emotionService.currentUserFindEmotions(user.getId(), "");
        double[] emotionsAvgUser = Ut.emotionsAvgCreate(emotions);
        assertThat(emotionsAvgUser[0]).isEqualTo(30);
        assertThat(emotionsAvgUser[1]).isEqualTo(50);
        assertThat(emotionsAvgUser[2]).isEqualTo(20);
    }
}