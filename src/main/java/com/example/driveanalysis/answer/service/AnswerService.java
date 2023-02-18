package com.example.driveanalysis.answer.service;

import com.example.driveanalysis.user.entity.SiteUser;
import com.example.driveanalysis.answer.entity.Answer;
import com.example.driveanalysis.answer.repository.AnswerRepository;
import com.example.driveanalysis.aritlce.entity.Article;
import com.example.driveanalysis.base.exception.DataNotFoundException;
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
        answer.setArticle(article);

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
