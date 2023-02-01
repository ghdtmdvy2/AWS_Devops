package com.example.driveanalysis.controller;

import com.example.driveanalysis.cartitem.controller.CartItemController;
import com.example.driveanalysis.product.controller.ProductController;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
@WithUserDetails("admin")
class ProductControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    public void showProductList() throws Exception {
        ResultActions resultActions = mockMvc
                .perform(get("/product/list"))
                .andDo(print());
        resultActions
                .andExpect(status().is2xxSuccessful())
                .andExpect(handler().handlerType(ProductController.class))
                .andExpect(handler().methodName("showProductList"));
    }
    @Test
    public void showProduct() throws Exception {
        ResultActions resultActions = mockMvc
                .perform(get("/product/3"))
                .andDo(print());
        resultActions
                .andExpect(status().is2xxSuccessful())
                .andExpect(handler().handlerType(ProductController.class))
                .andExpect(handler().methodName("showProduct"));
    }

    @Test
    public void showCreateProduct() throws Exception {
        ResultActions resultActions = mockMvc
                .perform(get("/product/create"))
                .andDo(print());
        resultActions
                .andExpect(status().is2xxSuccessful())
                .andExpect(handler().handlerType(ProductController.class))
                .andExpect(handler().methodName("showCreateProduct"));
    }

    @Test
    public void createProduct() throws Exception {
        ResultActions resultActions = mockMvc
                .perform(post("/product/create")
                        .with(csrf())
                        .param("subject","상품 이름")
                        .param("content","상품 설명")
                        .param("price","400")
                        .param("stock","30"))
                .andDo(print());
        resultActions
                .andExpect(status().is3xxRedirection())
                .andExpect(handler().handlerType(ProductController.class))
                .andExpect(handler().methodName("createProduct"))
                .andExpect(redirectedUrl("/product/list"));
    }
    @Test
    public void deleteProduct() throws Exception {
        ResultActions resultActions = mockMvc
                .perform(post("/product/delete")
                        .with(csrf())
                        .param("productId","1"))
                .andDo(print());
        resultActions
                .andExpect(status().is3xxRedirection())
                .andExpect(handler().handlerType(ProductController.class))
                .andExpect(handler().methodName("deleteProduct"))
                .andExpect(redirectedUrl("/product/list"));
    }
}