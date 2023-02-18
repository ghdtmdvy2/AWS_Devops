package com.example.driveanalysis.controller;

import com.example.driveanalysis.cartitem.controller.CartItemController;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class CartItemControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void non_member_cartItem_confirm() throws Exception {
        ResultActions resultActions = mockMvc.
                perform(get("/cartItem/list"))
                .andDo(print());
        resultActions
                .andExpect(status().is3xxRedirection())
                .andExpect(handler().handlerType(CartItemController.class))
                .andExpect(handler().methodName("showCartItem"))
                .andExpect(redirectedUrlPattern("**/user/login"));
    }
    @Test
    @WithUserDetails("user1")
    public void member_cartItem_confirm() throws Exception {
        ResultActions resultActions = mockMvc.
                perform(get("/cartItem/list"))
                .andDo(print());
        resultActions
                .andExpect(status().is2xxSuccessful())
                .andExpect(handler().handlerType(CartItemController.class))
                .andExpect(handler().methodName("showCartItem"));
    }

    @Test
    @WithUserDetails("user1")
    public void member_cartItem_add() throws Exception {
        ResultActions resultActions = mockMvc.
                perform(post("/cartItem/create/1")
                        .with(csrf())
                        .param("productId","1")
                        .param("amount","1"))
                .andDo(print());
        resultActions
                .andExpect(status().is3xxRedirection())
                .andExpect(handler().handlerType(CartItemController.class))
                .andExpect(handler().methodName("createCartItem"))
                .andExpect(redirectedUrl("/product/list"));
    }

    @Test
    @WithUserDetails("user1")
    public void member_cartItem_delete() throws Exception {
        ResultActions resultActions = mockMvc.
                perform(post("/cartItem/removeItems")
                        .with(csrf())
                        .param("ids","4,5,6"))
                .andDo(print());
        resultActions
                .andExpect(status().is3xxRedirection())
                .andExpect(handler().handlerType(CartItemController.class))
                .andExpect(handler().methodName("removeItems"))
                .andExpect(redirectedUrl("/cartItem/list"));
    }
}