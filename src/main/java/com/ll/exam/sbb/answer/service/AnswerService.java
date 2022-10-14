package com.ll.exam.sbb.answer.service;

import com.ll.exam.sbb.answer.entity.Answer;
import com.ll.exam.sbb.answer.repository.AnswerRepository;
import com.ll.exam.sbb.aritlce.entity.Article;
import com.ll.exam.sbb.base.exception.DataNotFoundException;
import com.ll.exam.sbb.user.entity.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AnswerService {
    private final AnswerRepository answerRepository;

    public Answer create(Article article, String content, SiteUser author) {
        Answer answer = new Answer();
        answer.setContent(content);
        answer.setAuthor(author);
        article.addAnswer(answer);

        answerRepository.save(answer);

        return answer;
    }

    public Answer getAnswer(Long id) {
        return answerRepository.findById(id).orElseThrow(() -> new DataNotFoundException("answer not found"));
    }

    public void modify(Answer answer, String content) {
        answer.setContent(content);
        answerRepository.save(answer);
    }

    public void delete(Answer answer) {
        answerRepository.delete(answer);
    }

    public void vote(Answer answer, SiteUser siteUser) {
        answer.getVoter().add(siteUser);

        answerRepository.save(answer);
    }
}
