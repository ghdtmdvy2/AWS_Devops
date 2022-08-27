package com.ll.exam.sbb;

import com.ll.exam.sbb.answer.Answer;
import com.ll.exam.sbb.answer.AnswerRepository;
import com.ll.exam.sbb.question.Question;
import com.ll.exam.sbb.question.QuestionRepository;
import com.ll.exam.sbb.user.SiteUser;
import com.ll.exam.sbb.user.UserRepository;
import com.ll.exam.sbb.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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

    @BeforeEach
    void beforeEach() {
        clearData();
        createSampleData();
    }

    public static void clearData(UserRepository userRepository, AnswerRepository answerRepository, QuestionRepository questionRepository) {
        UserServiceTests.clearData(userRepository, answerRepository, questionRepository);
    }
    private void clearData() {
        clearData(userRepository, answerRepository, questionRepository);
    }

    private void createSampleData() {
        QuestionRepositoryTests.createSampleData(userService, questionRepository);

        // 관련 답변이 하나없는 상태에서 쿼리 발생
        Question q = questionRepository.findById(1L).get();

        Answer a1 = new Answer();
        a1.setContent("sbb는 질문답변 게시판 입니다.");
        a1.setAuthor(new SiteUser(1L));
        a1.setCreateDate(LocalDateTime.now());
        q.addAnswer(a1);

        Answer a2 = new Answer();
        a2.setContent("sbb에서는 주로 스프링부트관련 내용을 다룹니다.");
        a2.setAuthor(new SiteUser(2L));
        a2.setCreateDate(LocalDateTime.now());
        q.addAnswer(a2);

        questionRepository.save(q);
    }

}
