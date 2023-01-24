package com.ll.exam.sbb.controller;

import com.ll.exam.sbb.answer.controller.AnswerController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class AnswerControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithUserDetails("user1")
    void get_article_list() throws Exception {
        ResultActions resultActions = mockMvc
                .perform(post("/answer/create/1")
                        .with(csrf())
                        .param("content", "댓글 내용1")
                )
                .andDo(print());
        resultActions
                .andExpect(status().is3xxRedirection())
                .andExpect(handler().handlerType(AnswerController.class))
                .andExpect(handler().methodName("detail"))
                .andExpect(redirectedUrlPattern("/article/detail/1#*"));
    }
}