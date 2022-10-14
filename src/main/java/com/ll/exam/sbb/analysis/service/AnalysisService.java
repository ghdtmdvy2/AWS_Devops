package com.ll.exam.sbb.analysis.service;

import com.ll.exam.sbb.analysis.entity.Analysis;
import com.ll.exam.sbb.analysis.repository.AnalysisRepository;
import com.ll.exam.sbb.base.exception.DataNotFoundException;
import com.ll.exam.sbb.user.entity.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AnalysisService {
    private final AnalysisRepository analysisRepository;

    public Page<Analysis> getList(String kw, int page, String sortCode, long id) {
        List<Sort.Order> sorts = new ArrayList<>();

        switch (sortCode) {
            case "OLD" -> sorts.add(Sort.Order.asc("id")); // 오래된순
            default -> sorts.add(Sort.Order.desc("id")); // 최신순
        }

        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts)); // 한 페이지에 10까지 가능

        if ( kw == null || kw.trim().length() == 0 || kw.equals("null")) {
            return analysisRepository.findAllByAuthor_Id(pageable,id);
        }

        return analysisRepository.findDistinctBySubjectContainsOrContentContainsOrAuthor_usernameContainsOrAuthor_Id(kw, kw, kw, id, pageable);
    }

    public Analysis getAnalysis(long id) {
        return analysisRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("no %d question not found,".formatted(id)));
    }

    public void create(String subject, String content, SiteUser author) {
        Analysis q = new Analysis();
        q.setSubject(subject);
        q.setContent(content);
        q.setAuthor(author);
        analysisRepository.save(q);
    }

    public void modify(Analysis analysis, String subject, String content) {
        analysis.setSubject(subject);
        analysis.setContent(content);
        analysisRepository.save(analysis);
    }

    public void delete(Analysis analysis) {
        this.analysisRepository.delete(analysis);
    }

    public void vote(Analysis analysis, SiteUser siteUser) {
        analysis.getVoter().add(siteUser);

        analysisRepository.save(analysis);
    }
}
