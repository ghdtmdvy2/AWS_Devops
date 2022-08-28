package com.ll.exam.sbb;

import com.ll.exam.sbb.answer.Answer;
import com.ll.exam.sbb.answer.AnswerRepository;
import com.ll.exam.sbb.emotion.Emotion;
import com.ll.exam.sbb.emotion.EmotionRepository;
import com.ll.exam.sbb.question.Question;
import com.ll.exam.sbb.question.QuestionRepository;
import com.ll.exam.sbb.user.SiteUser;
import com.ll.exam.sbb.user.UserRepository;
import com.ll.exam.sbb.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@SpringBootTest
public class EmotionRepositoryTests {
    @Autowired
    private UserService userService;

    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private AnswerRepository answerRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmotionRepository emotionRepository;

    @BeforeEach
    void beforeEach() {
        clearData();
        createSampleData();
    }

    public static void clearData(UserRepository userRepository, AnswerRepository answerRepository, QuestionRepository questionRepository, EmotionRepository emotionRepository) {
        UserServiceTests.clearData(userRepository, answerRepository, questionRepository, emotionRepository);
    }
    private void clearData() {
        clearData(userRepository, answerRepository, questionRepository, emotionRepository);
    }

    private void createSampleData() {
        QuestionRepositoryTests.createSampleData(userService, questionRepository);

    }

    @Test
    @Transactional
    @Rollback(false)
    void test() {
        boolean run = true;
        if (run == false) return;
        // 관련 답변이 하나없는 상태에서 쿼리 발생
        Question q = questionRepository.findById(1L).get();

        for (int i = 0; i<100; i++) {
            Emotion e1 = new Emotion();
            e1.setAngry(24.5);
            e1.setHappy(25.4);
            e1.setNeutral(50.1);
            e1.setAuthor(new SiteUser(1L));
            q.addEmotion(e1);
        }

        questionRepository.save(q);
    }

}
