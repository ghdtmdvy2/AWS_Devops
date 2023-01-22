package com.ll.exam.sbb.user.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Test
    void getSignup() throws Exception {
        ResultActions resultActions = mockMvc
                .perform(get("/user/signup"))
                .andDo(print());
        resultActions
                .andExpect(status().is2xxSuccessful())
                .andExpect(handler().handlerType(UserController.class))
                .andExpect(handler().methodName("signup"))
                .andExpect(content().string(containsString("<h4>회원가입</h4>")));
    }

    @Test
    void postSignup() throws Exception {
        ResultActions resultActions = mockMvc
                .perform(post("/user/signup")
                        .with(csrf())
                        .param("username", "user999")
                        .param("password1", "1234")
                        .param("password2", "1234")
                        .param("email", "user999@test.com")
                )
                .andDo(print());
        resultActions
                .andExpect(status().is3xxRedirection())
                .andExpect(handler().handlerType(UserController.class))
                .andExpect(handler().methodName("signup"))
                .andExpect(redirectedUrl("/"));
    }
}