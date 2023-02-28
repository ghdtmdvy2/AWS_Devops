package com.example.driveanalysis.analysis.service;

import com.example.driveanalysis.analysis.entity.Analysis;
import com.example.driveanalysis.base.exception.DataNotFoundException;
import com.example.driveanalysis.user.entity.SiteUser;
import com.example.driveanalysis.analysis.repository.AnalysisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AnalysisService {
    private final AnalysisRepository analysisRepository;


    public Page<Analysis> getAnalysisList(String kw, int page, String sortCode, long id) {
        List<Sort.Order> sorts = new ArrayList<>();

        switch (sortCode) {
            case "OLD" -> sorts.add(Sort.Order.asc("id")); // 오래된순
            default -> sorts.add(Sort.Order.desc("id")); // 최신순
        }

        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts)); // 한 페이지에 10까지 가능

        if ( kw == null || kw.trim().length() == 0 || kw.equals("null")) {
            return analysisRepository.findAllByAuthorId(pageable,id);
        }

        return analysisRepository.findDistinctByAuthor_usernameContainsOrAuthorId(kw, kw, kw, id, pageable);
    }

    public Analysis getAnalysis(long analysisId) {
        return analysisRepository.findByAnalysisIds(analysisId)
                .orElseThrow(() -> new DataNotFoundException("no %d analysis not found,".formatted(analysisId)));
    }


    @Transactional
    public Analysis create(SiteUser author) {
        Analysis analysis = new Analysis();
        analysis.setAuthor(author);
        analysisRepository.save(analysis);
        return analysis;
    }
    @Transactional
    public void delete(Analysis analysis) {
        this.analysisRepository.delete(analysis);
    }

}
