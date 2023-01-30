package com.example.driveanalysis.controller;

import com.example.driveanalysis.order.controller.ProductOrderController;
import com.example.driveanalysis.user.controller.UserController;
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
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class ProductOrderControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Test
    @WithUserDetails("user1")
    void showDetail() throws Exception {
        ResultActions resultActions = mockMvc
                .perform(get("/order/1"))
                .andDo(print());
        resultActions
                .andExpect(status().is2xxSuccessful())
                .andExpect(handler().handlerType(ProductOrderController.class))
                .andExpect(handler().methodName("showDetail"));
    }
    @Test
    @WithUserDetails("user1")
    void makeOrder() throws Exception {
        ResultActions resultActions = mockMvc
                .perform(post("/order/makeOrder")
                        .with(csrf()))
                .andDo(print());
        resultActions
                .andExpect(status().is3xxRedirection())
                .andExpect(handler().handlerType(ProductOrderController.class))
                .andExpect(handler().methodName("makeOrder"))
                .andExpect(redirectedUrlPattern("/order/**"));
    }
}