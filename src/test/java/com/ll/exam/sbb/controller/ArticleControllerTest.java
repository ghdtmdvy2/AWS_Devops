package com.ll.exam.sbb.controller;

import com.ll.exam.sbb.aritlce.controller.ArticleController;
import com.ll.exam.sbb.user.controller.UserController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class ArticleControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void get_article_list() throws Exception {
        ResultActions resultActions = mockMvc
                .perform(get("/article/list"))
                .andDo(print());
        resultActions
                .andExpect(status().is2xxSuccessful())
                .andExpect(handler().handlerType(ArticleController.class))
                .andExpect(handler().methodName("list"));
    }

    @Test
    void get_detail_article() throws Exception {
        ResultActions resultActions = mockMvc
                .perform(get("/article/detail/1"))
                .andDo(print());
        resultActions
                .andExpect(status().is2xxSuccessful())
                .andExpect(handler().handlerType(ArticleController.class))
                .andExpect(handler().methodName("detail"));
    }

    @Test
    @WithUserDetails("user1")
    void get_modify_article() throws Exception {
        ResultActions resultActions = mockMvc
                .perform(get("/article/modify/1"))
                .andDo(print());
        resultActions
                .andExpect(status().is2xxSuccessful())
                .andExpect(handler().handlerType(ArticleController.class))
                .andExpect(handler().methodName("articleModify"));
    }

    @Test
    @WithUserDetails("user1")
    void post_modify_article() throws Exception {
        ResultActions resultActions = mockMvc
                .perform(post("/article/modify/1")
                        .with(csrf())
                        .param("subject", "수정된 제목1")
                        .param("content", "수정된 내용1")
                )
                .andDo(print());
        resultActions
                .andExpect(status().is3xxRedirection())
                .andExpect(handler().handlerType(ArticleController.class))
                .andExpect(handler().methodName("articleModify"))
                .andExpect(redirectedUrl("/article/detail/1"));
    }

    @Test
    void get_non_member_create_article() throws Exception {
        ResultActions resultActions = mockMvc
                .perform(get("/article/create"))
                .andDo(print());
        resultActions
                .andExpect(status().is3xxRedirection())
                .andExpect(handler().handlerType(ArticleController.class))
                .andExpect(handler().methodName("articleCreate"));
    }

    @Test
    @WithUserDetails("user1")
    void get_create_article() throws Exception {
        ResultActions resultActions = mockMvc
                .perform(get("/article/create"))
                .andDo(print());
        resultActions
                .andExpect(status().is2xxSuccessful())
                .andExpect(handler().handlerType(ArticleController.class))
                .andExpect(handler().methodName("articleCreate"));
    }

    @Test
    @WithUserDetails("user1")
    void post_create_article() throws Exception {
        ResultActions resultActions = mockMvc
                .perform(post("/article/create")
                        .with(csrf())
                        .param("subject", "제목1")
                        .param("content", "내용1")
                )
                .andDo(print());
        resultActions
                .andExpect(status().is3xxRedirection())
                .andExpect(handler().handlerType(ArticleController.class))
                .andExpect(handler().methodName("articleCreate"))
                .andExpect(redirectedUrl("/article/list"));
    }

    @Test
    @WithUserDetails("user1")
    void get_delete_article() throws Exception {
        ResultActions resultActions = mockMvc
                .perform(get("/article/delete/1"))
                .andDo(print());
        resultActions
                .andExpect(status().is3xxRedirection())
                .andExpect(handler().handlerType(ArticleController.class))
                .andExpect(handler().methodName("articleDelete"))
                .andExpect(redirectedUrl("/article/list"));
    }
}