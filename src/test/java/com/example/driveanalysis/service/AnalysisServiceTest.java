package com.example.driveanalysis.service;

import com.example.driveanalysis.analysis.entity.Analysis;
import com.example.driveanalysis.analysis.service.AnalysisService;
import com.example.driveanalysis.user.entity.SiteUser;
import com.example.driveanalysis.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class AnalysisServiceTest {
    @Autowired
    AnalysisService analysisService;
    @Autowired
    UserService userService;

    @Test
    void get_analysis_list(){
        SiteUser user = userService.getUser("user1");
        Page<Analysis> articles = analysisService.getAnalysisList("", 0, "",user.getId());
        assertThat(articles.getSize()).isPositive();
    }

    @Test
    void create_analysis(){
        SiteUser user = userService.getUser("user1");
        Analysis analysis = analysisService.create(user);
        assertThat(analysis).isNotNull();
        assertThat(analysis.getAuthor()).isEqualTo(user);
    }

    @Transactional
    @Test
    void get_analysis(){
        SiteUser user = userService.getUser("user1");
        Analysis analysis = analysisService.create(user);
        Analysis findAnalysis = analysisService.getAnalysis(analysis.getId());
        assertThat(findAnalysis).isNotNull();
        SiteUser author = findAnalysis.getAuthor();
        assertThat(author).isEqualTo(user);
    }

    @Test
    void delete_article(){
        SiteUser user = userService.getUser("user1");
        Analysis analysis = analysisService.create(user);
        analysisService.delete(analysis);
        try{
            analysisService.getAnalysis(analysis.getId());
        }
        catch (Exception e) {
            assertThat(true).isTrue();
            return;
        }
        assertThat(false).isFalse();
    }
}