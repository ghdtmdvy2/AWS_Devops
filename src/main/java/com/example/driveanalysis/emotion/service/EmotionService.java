package com.example.driveanalysis.emotion.service;

import com.example.driveanalysis.base.util.Ut;
import com.example.driveanalysis.emotion.repository.EmotionRepository;
import com.example.driveanalysis.analysis.entity.Analysis;
import com.example.driveanalysis.emotion.entity.Emotion;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmotionService {
    private final EmotionRepository emotionRepository;
    public List<Emotion> currentUserFindEmotions(Long authorId, String yearMonth) {
        if (yearMonth == null || yearMonth.trim().length() == 0){
            return emotionRepository.findAllByAuthor_id(authorId);
        }
        int monthEndDay = Ut.date.getEndDayOf(yearMonth);

        String fromDateStr = yearMonth + "-01 00:00:00.000000";
        String toDateStr = yearMonth + "-%02d 23:59:59.999999".formatted(monthEndDay);
        LocalDateTime fromDate = Ut.date.parse(fromDateStr);
        LocalDateTime toDate = Ut.date.parse(toDateStr);

        return emotionRepository.findAllByCreatedDateBetweenAndAuthor_id(fromDate,toDate,authorId);
    }

    public List<Emotion> AllUsersFindEmotions(String yearMonth) {
        if (yearMonth == null || yearMonth.trim().length() == 0){
            return emotionRepository.findAll();
        }
        int monthEndDay = Ut.date.getEndDayOf(yearMonth);

        String fromDateStr = yearMonth + "-01 00:00:00.000000";
        String toDateStr = yearMonth + "-%02d 23:59:59.999999".formatted(monthEndDay);
        LocalDateTime fromDate = Ut.date.parse(fromDateStr);
        LocalDateTime toDate = Ut.date.parse(toDateStr);

        return emotionRepository.findAllByCreatedDateBetween(fromDate,toDate);
    }

    public void emotionCreate(Analysis analysis, double angry, double happy, double neutral) {
        Emotion emotion = new Emotion();
        emotion.setAnalysis(analysis);
        emotion.setAuthor(analysis.getAuthor());
        emotion.setAngry(angry);
        emotion.setHappy(happy);
        emotion.setNeutral(neutral);
        emotion.setCreatedDate(LocalDateTime.now());
        emotionRepository.save(emotion);
    }

    public void testDataEmotionCreate(Analysis analysis, double angry, double happy, double neutral, String createDate) {
        Emotion emotion = new Emotion();
        emotion.setAnalysis(analysis);
        emotion.setAuthor(analysis.getAuthor());
        emotion.setAngry(angry);
        emotion.setHappy(happy);
        emotion.setNeutral(neutral);
        LocalDateTime createDateEmotion = Ut.date.parse(createDate);
        emotion.setCreatedDate(createDateEmotion);
        emotionRepository.save(emotion);
    }
}
