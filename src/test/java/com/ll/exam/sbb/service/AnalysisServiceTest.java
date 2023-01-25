package com.ll.exam.sbb.service;

import com.ll.exam.sbb.analysis.entity.Analysis;
import com.ll.exam.sbb.analysis.service.AnalysisService;
import com.ll.exam.sbb.aritlce.entity.Article;
import com.ll.exam.sbb.user.entity.SiteUser;
import com.ll.exam.sbb.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class AnalysisServiceTest {
    @Autowired
    AnalysisService analysisService;
    @Autowired
    UserService userService;

    @Test
    public void get_analysis_list(){
        SiteUser user = userService.getUser("user1");
        Page<Analysis> articles = analysisService.getAnalysisList("", 0, "",user.getId());
        assertThat(articles.getSize()).isGreaterThan(0);
    }

    @Test
    public void create_analysis(){
        SiteUser user = userService.getUser("user1");
        String subject = "user1의 감정 기록 제목1";
        String content = "user1의 감정 기록 내용1";
        Analysis analysis = analysisService.create(subject,content,user);
        assertThat(analysis).isNotNull();
        assertThat(analysis.getSubject()).isEqualTo(subject);
        assertThat(analysis.getContent()).isEqualTo(content);
        assertThat(analysis.getAuthor()).isEqualTo(user);
    }

    @Test
    public void get_analysis(){
        SiteUser user = userService.getUser("user1");
        String subject = "제목1";
        String content = "내용1";
        Analysis analysis = analysisService.create(subject,content,user);
        Analysis findAnalysis = analysisService.getAnalysis(analysis.getId());
        assertThat(findAnalysis).isNotNull();
        assertThat(findAnalysis.getSubject()).isEqualTo(subject);
        assertThat(findAnalysis.getContent()).isEqualTo(content);
        assertThat(findAnalysis.getAuthor()).isEqualTo(user);
    }
}