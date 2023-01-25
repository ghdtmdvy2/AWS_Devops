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
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class AnalysisServiceTest {
    @Autowired
    AnalysisService analysisService;
    @Autowired
    UserService userService;

    @Test
    public void get_analysis(){
        SiteUser user = userService.getUser("user1");
        Page<Analysis> articles = analysisService.getList("", 0, "",user.getId());
        assertThat(articles.getSize()).isGreaterThan(0);
    }
}