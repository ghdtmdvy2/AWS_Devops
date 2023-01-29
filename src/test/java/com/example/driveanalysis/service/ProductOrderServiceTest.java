package com.example.driveanalysis.service;

import com.example.driveanalysis.order.entity.ProductOrder;
import com.example.driveanalysis.order.service.ProductOrderService;
import com.example.driveanalysis.product.entity.Product;
import com.example.driveanalysis.product.service.ProductService;
import com.example.driveanalysis.user.entity.SiteUser;
import com.example.driveanalysis.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class ProductOrderServiceTest {
    @Autowired
    ProductOrderService productOrderService;

    @Autowired
    UserService userService;

    @Autowired
    ProductService productService;
    @Test
    public void create_productOrder(){
        SiteUser user1 = userService.getUser("user1");
        Product product = productService.getProducts(user1).get(0);
        ProductOrder productOrder = productOrderService.createProductOrder(product, user1);
    }
}