package com.ll.exam.sbb.emotion.service;

import com.ll.exam.sbb.base.util.Ut;
import com.ll.exam.sbb.emotion.entity.Emotion;
import com.ll.exam.sbb.emotion.repository.EmotionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmotionService {
    private final EmotionRepository emotionRepository;
    public List<Emotion> findByAuthor_id(Long id, String yearMonth) {
        if (yearMonth == null || yearMonth.trim().length() == 0){
            return emotionRepository.findAllByAuthor_id(id);
        }
        int monthEndDay = Ut.date.getEndDayOf(yearMonth);

        String fromDateStr = yearMonth + "-01 00:00:00.000000";
        String toDateStr = yearMonth + "-%02d 23:59:59.999999".formatted(monthEndDay);
        LocalDateTime fromDate = Ut.date.parse(fromDateStr);
        LocalDateTime toDate = Ut.date.parse(toDateStr);

        return emotionRepository.findAllByCreatedDateBetweenAndAuthor_id(fromDate,toDate,id);
    }

    public List<Emotion> findAll(String yearMonth) {
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
}
