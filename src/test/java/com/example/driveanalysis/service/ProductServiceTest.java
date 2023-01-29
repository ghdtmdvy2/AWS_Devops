package com.example.driveanalysis.service;

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
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class ProductServiceTest {
    @Autowired
    ProductService productService;

    @Autowired
    UserService userService;
    @Test
    public void product_create(){
        SiteUser user = userService.getUser("user1");
        String subject = "상품1";
        String content = "상품 이름";
        int price = 10_000;
        Product product = productService.create(subject, content, price, user);
        assertThat(product).isNotNull();
        assertThat(product.getSubject()).isEqualTo(subject);
        assertThat(product.getContent()).isEqualTo(content);
        assertThat(product.getPrice()).isEqualTo(price);
        assertThat(product.getAuthor()).isEqualTo(user);
    }

    @Test
    public void product_list_find(){
        SiteUser user = userService.getUser("user1");
        List<Product> products = productService.getProducts(user);
        assertThat(products.size()).isEqualTo(2);
    }

    @Test
    public void product_find(){
        SiteUser user = userService.getUser("user1");
        List<Product> products = productService.getProducts(user);
        Optional<Product> O_product = productService.getProduct(products.get(0));
        Product product = O_product.get();
        assertThat(product).isNotNull();
    }

    @Test
    public void product_modify(){
        SiteUser user = userService.getUser("user1");
        String oldSubject = "상품1";
        String oldContent = "상품 이름";
        int oldPrice = 10_000;
        Product product = productService.create(oldSubject, oldContent, oldPrice, user);
        assertThat(product).isNotNull();
        assertThat(product.getSubject()).isEqualTo(oldSubject);
        assertThat(product.getContent()).isEqualTo(oldContent);
        assertThat(product.getPrice()).isEqualTo(oldPrice);
        assertThat(product.getAuthor()).isEqualTo(user);
        String subject = "수정 상품1";
        String content = "수정 상품 이름";
        int price = 100_000;
        productService.modify(product, subject,content,price);
        Product currentProduct = productService.getProduct(product).get();
        assertThat(currentProduct).isNotNull();
        assertThat(currentProduct.getSubject()).isEqualTo(subject);
        assertThat(currentProduct.getContent()).isEqualTo(content);
        assertThat(currentProduct.getPrice()).isEqualTo(price);
        assertThat(currentProduct.getAuthor()).isEqualTo(user);
    }

    @Test
    public void product_delete(){
        SiteUser user = userService.getUser("user1");
        String subject = "상품1";
        String content = "상품 이름";
        int price = 10_000;
        Product product = productService.create(subject, content, price, user);
        assertThat(product).isNotNull();
        assertThat(product.getSubject()).isEqualTo(subject);
        assertThat(product.getContent()).isEqualTo(content);
        assertThat(product.getPrice()).isEqualTo(price);
        assertThat(product.getAuthor()).isEqualTo(user);
        productService.delete(product);
        Optional<Product> deletedProduct = productService.getProduct(product);
        assertThat(deletedProduct.isEmpty()).isTrue();
    }

}