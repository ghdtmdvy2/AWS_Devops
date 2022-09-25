package com.ll.exam.sbb.emotion.service;

import com.ll.exam.sbb.emotion.entity.Emotion;
import com.ll.exam.sbb.emotion.repository.EmotionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class EmotionService {
    private final EmotionRepository emotionRepository;
    public ArrayList<Emotion> findByAuthor_id(Long id) {
        return emotionRepository.findByAuthor_id(id);
    }
}
