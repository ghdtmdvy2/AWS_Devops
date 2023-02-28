package com.example.driveanalysis.service;

import com.example.driveanalysis.cartitem.service.CartItemService;
import com.example.driveanalysis.cashlog.entity.CashLog;
import com.example.driveanalysis.cashlog.service.CashLogService;
import com.example.driveanalysis.order.entity.OrderItem;
import com.example.driveanalysis.order.entity.ProductOrder;
import com.example.driveanalysis.order.service.ProductOrderService;
import com.example.driveanalysis.product.service.ProductService;
import com.example.driveanalysis.user.entity.SiteUser;
import com.example.driveanalysis.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
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

    @Autowired
    CashLogService cashLogService;

    @Test
    @Transactional
    void create_from_cart_productOrder(){
        SiteUser user1 = userService.getUser("user6");
        ProductOrder productOrder = productOrderService.createFromCartProductOrder(user1);

        OrderItem orderItem1 = productOrder.getOrderItems().get(0);
        OrderItem orderItem2 = productOrder.getOrderItems().get(1);
        OrderItem orderItem3 = productOrder.getOrderItems().get(2);

        assertThat(productOrder.getOrderer()).isEqualTo(user1);
        assertThat(productOrder.getOrderItems()).hasSize(3);
        assertThat(productOrder.getCreatedDate()).isBefore(LocalDateTime.now());
        assertThat(orderItem1.getAmount()).isEqualTo(2);
        assertThat(orderItem2.getAmount()).isEqualTo(1);
        assertThat(orderItem3.getAmount()).isEqualTo(1);
    }
    @Test
    @Transactional
    void pay_toss_payments(){
        SiteUser user = userService.getUser("user3");
        ProductOrder productOrder = productOrderService.createFromCartProductOrder(user);
        productOrderService.payTossPayments(productOrder);
        ProductOrder payProductOrder = productOrderService.findProductOrder(productOrder.getId());
        List<CashLog> cashLogs = cashLogService.getCashLog(user.getId());
        CashLog chargeMoneyCashLog = cashLogs.get(0);
        CashLog useMoneyCashLog = cashLogs.get(1);

        assertThat(payProductOrder.getPayDate()).isBefore(LocalDateTime.now());
        assertThat(payProductOrder.isPaid()).isTrue();

        int pgPayPrice = payProductOrder.calculatePay();
        assertThat(chargeMoneyCashLog.getPrice()).isEqualTo(pgPayPrice);
        assertThat(chargeMoneyCashLog.getEventType()).isEqualTo("주문__%d__충전__토스페이먼츠".formatted(payProductOrder.getId()));

        assertThat(useMoneyCashLog.getPrice()).isEqualTo(pgPayPrice * -1);
        assertThat(useMoneyCashLog.getEventType()).isEqualTo("주문__%d__사용__토스페이먼츠".formatted(payProductOrder.getId()));
    }
    @Test
    void findProductOrders(){
        SiteUser user = userService.getUser("user1");
        List<ProductOrder> payProductOrders = productOrderService.findProductOrders(user.getId());
        assertThat(payProductOrders).hasSize(2);
    }
}