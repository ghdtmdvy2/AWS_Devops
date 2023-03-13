package com.example.driveanalysis.service;

import com.example.driveanalysis.base.exception.DataNotFoundException;
import com.example.driveanalysis.product.entity.Product;
import com.example.driveanalysis.product.service.ProductService;
import com.example.driveanalysis.user.entity.SiteUser;
import com.example.driveanalysis.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@ActiveProfiles("test")
@Transactional
class ProductServiceTest {
    @Autowired
    ProductService productService;

    @Autowired
    UserService userService;
    @Test
    void product_create(){
        SiteUser user = userService.getUser("user1");
        String subject = "상품1";
        String content = "상품 이름";
        int price = 10_000;
        Product product = productService.create(subject, content, price, user,150);
        assertThat(product).isNotNull();
        assertThat(product.getSubject()).isEqualTo(subject);
        assertThat(product.getContent()).isEqualTo(content);
        assertThat(product.getStock()).isEqualTo(150);
        assertThat(product.isOrderable()).isTrue();
        assertThat(product.getPrice()).isEqualTo(price);
        assertThat(product.getAuthor()).isEqualTo(user);
    }

    @Test
    void product_list_find(){
        SiteUser user = userService.getUser("user1");
        List<Product> products = productService.getUserProducts(user);
        assertThat(products).hasSize(2);
    }

    @Test
    void product_find(){
        SiteUser user = userService.getUser("user1");
        List<Product> products = productService.getUserProducts(user);
        Product product = productService.getProduct(products.get(0).getId());
        assertThat(product).isNotNull();
    }

    @Test
    void product_modify(){
        SiteUser user = userService.getUser("user1");
        String oldSubject = "상품1";
        String oldContent = "상품 이름";
        int oldPrice = 10_000;
        Product product = productService.create(oldSubject, oldContent, oldPrice, user, 150);
        assertThat(product).isNotNull();
        assertThat(product.getSubject()).isEqualTo(oldSubject);
        assertThat(product.getContent()).isEqualTo(oldContent);
        assertThat(product.getPrice()).isEqualTo(oldPrice);
        assertThat(product.getStock()).isEqualTo(150);
        assertThat(product.isOrderable()).isTrue();
        assertThat(product.getAuthor()).isEqualTo(user);
        String subject = "수정 상품1";
        String content = "수정 상품 이름";
        int price = 100_000;
        productService.modify(product, subject,content,price);
        Product currentProduct = productService.getProduct(product.getId());
        assertThat(currentProduct).isNotNull();
        assertThat(currentProduct.getSubject()).isEqualTo(subject);
        assertThat(currentProduct.getContent()).isEqualTo(content);
        assertThat(product.getStock()).isEqualTo(150);
        assertThat(product.isOrderable()).isTrue();
        assertThat(currentProduct.getPrice()).isEqualTo(price);
        assertThat(currentProduct.getAuthor()).isEqualTo(user);
    }

    @Test
    void product_delete(){
        SiteUser user = userService.getUser("user1");
        String subject = "상품1";
        String content = "상품 이름";
        int price = 10_000;
        Product product = productService.create(subject, content, price, user, 150);
        assertThat(product).isNotNull();
        assertThat(product.getSubject()).isEqualTo(subject);
        assertThat(product.getContent()).isEqualTo(content);
        assertThat(product.getPrice()).isEqualTo(price);
        assertThat(product.getStock()).isEqualTo(150);
        assertThat(product.isOrderable()).isTrue();
        assertThat(product.getAuthor()).isEqualTo(user);
        productService.delete(product.getId());
        Product deletedProduct = null;
        try {
            deletedProduct = productService.getProduct(product.getId());
        }catch (DataNotFoundException e) {

        }
        assertThat(deletedProduct).isNull();
    }

}