package com.example.driveanalysis.service;

import com.example.driveanalysis.cartitem.service.CartItemService;
import com.example.driveanalysis.order.entity.OrderItem;
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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class ProductOrderServiceTest {
    @Autowired
    ProductOrderService productOrderService;

    @Autowired
    CartItemService cartItemService;
    @Autowired
    UserService userService;

    @Autowired
    ProductService productService;
    @Test
    public void create_from_cart_productOrder(){
        SiteUser user1 = userService.getUser("user1");
        ProductOrder productOrder = productOrderService.createFromCartProductOrder(user1);

        OrderItem orderItem1 = productOrder.getOrderItems().get(0);
        OrderItem orderItem2 = productOrder.getOrderItems().get(1);
        OrderItem orderItem3 = productOrder.getOrderItems().get(2);

        assertThat(productOrder.getOrderer()).isEqualTo(user1);
        assertThat(productOrder.getOrderItems().size()).isEqualTo(3);
        assertThat(productOrder.getCreatedDate()).isBefore(LocalDateTime.now());
        assertThat(orderItem1.getAmount()).isEqualTo(2);
        assertThat(orderItem2.getAmount()).isEqualTo(1);
        assertThat(orderItem3.getAmount()).isEqualTo(1);
    }
}