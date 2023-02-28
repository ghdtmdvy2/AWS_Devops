package com.example.driveanalysis.controller;

import com.example.driveanalysis.analysis.controller.AnalysisController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@WithUserDetails("user1")
class AnalysisControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void get_analysis_list() throws Exception {
        ResultActions resultActions = mockMvc
                .perform(get("/analysis/1"))
                .andDo(print());
        resultActions
                .andExpect(status().is2xxSuccessful())
                .andExpect(handler().handlerType(AnalysisController.class))
                .andExpect(handler().methodName("list"));
    }

    @Test
    void get_detail_analysis() throws Exception {
        ResultActions resultActions = mockMvc
                .perform(get("/analysis/2/1"))
                .andDo(print());
        resultActions
                .andExpect(status().is2xxSuccessful())
                .andExpect(handler().handlerType(AnalysisController.class))
                .andExpect(handler().methodName("detail"));
    }

    @Test
    void get_delete_analysis() throws Exception {
        ResultActions resultActions = mockMvc
                .perform(get("/analysis/delete/1"))
                .andDo(print());
        resultActions
                .andExpect(status().is3xxRedirection())
                .andExpect(handler().handlerType(AnalysisController.class))
                .andExpect(handler().methodName("analysisDelete"))
                .andExpect(redirectedUrl("/analysis/list/2"));
    }
}