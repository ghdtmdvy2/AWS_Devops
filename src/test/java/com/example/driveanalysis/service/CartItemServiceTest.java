package com.example.driveanalysis.service;

import com.example.driveanalysis.cartitem.entity.CartItem;
import com.example.driveanalysis.cartitem.repository.CartItemRepository;
import com.example.driveanalysis.cartitem.service.CartItemService;
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

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class CartItemServiceTest {
    @Autowired
    UserService userService;

    @Autowired
    ProductService productService;

    @Autowired
    CartItemService cartItemService;
    @Autowired
    CartItemRepository cartItemRepository;

    @Test
    public void find_cartItem(){
        SiteUser user = userService.getUser("user1");
        List<CartItem> cartItems = cartItemService.findCartItems(user.getId());
        int cartItemAmount = 0;
        for (CartItem cartItem : cartItems) {
            cartItemAmount += cartItem.getAmount();
        }
        assertThat(cartItemAmount).isEqualTo(4);
    }

    @Test
    public void product_add_cartItem() {
        SiteUser user = userService.getUser("user2");
        List<Product> products = productService.getProducts(user);
        for (Product product : products) {
            cartItemService.addCartItem(product, user);
        }
        cartItemService.addCartItem(products.get(0), user);
        List<CartItem> cartItems = cartItemService.findCartItems(user.getId());
        int cartItemAmount = 0;
        for (CartItem cartItem : cartItems) {
            cartItemAmount += cartItem.getAmount();
        }
        assertThat(cartItemAmount).isEqualTo(3);
    }

    @Test
    public void delete_cartItem(){
        SiteUser user = userService.getUser("user2");
        List<Product> products = productService.getProducts(user);
        for (Product product : products) {
            CartItem cartItem = cartItemService.addCartItem(product, user);
            cartItemService.deleteCartItem(cartItem);
        }
        List<CartItem> cartItems = cartItemService.findCartItems(user.getId());
        int cartItemAmount = 0;
        for (CartItem cartItem : cartItems) {
            cartItemAmount += cartItem.getAmount();
        }
        assertThat(cartItemAmount).isEqualTo(0);
    }
}