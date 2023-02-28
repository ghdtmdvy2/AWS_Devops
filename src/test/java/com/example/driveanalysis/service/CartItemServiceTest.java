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
    @Transactional
    void find_cartItem(){
        SiteUser user = userService.getUser("user4");
        List<CartItem> cartItems = cartItemService.findCartItems(user.getId());
        int cartItemAmount = 0;
        for (CartItem cartItem : cartItems) {
            cartItemAmount += cartItem.getAmount();
        }
        assertThat(cartItemAmount).isEqualTo(4);
    }

    @Test
    void product_add_cartItem() {
        SiteUser user2 = userService.getUser("user2");
        SiteUser user4 = userService.getUser("user4");
        List<Product> products = productService.getUserProducts(user2);
        for (Product product : products) {
            cartItemService.addCartItem(product, user4, 1);
        }
        cartItemService.addCartItem(products.get(0), user4, 1);
        List<CartItem> cartItems = cartItemService.findCartItems(user4.getId());
        int cartItemAmount = 0;
        for (CartItem cartItem : cartItems) {
            cartItemAmount += cartItem.getAmount();
        }
        assertThat(cartItemAmount).isEqualTo(7);
    }

    @Test
    void delete_cartItem(){
        SiteUser user2 = userService.getUser("user2");
        SiteUser user5 = userService.getUser("user5");
        List<Product> products = productService.getUserProducts(user2);
        for (Product product : products) {
            CartItem cartItem = cartItemService.addCartItem(product, user5,1);
            cartItemService.deleteCartItem(cartItem.getId());
        }
        List<CartItem> cartItems = cartItemService.findCartItems(user5.getId());
        int cartItemAmount = 0;
        for (CartItem cartItem : cartItems) {
            cartItemAmount += cartItem.getAmount();
        }
        assertThat(cartItemAmount).isZero();
    }
    @Test
    @Transactional
    void increase_cartItems_quantity(){
        SiteUser user = userService.getUser("user4");
        List<CartItem> prevCartItems = cartItemService.findCartItems(user.getId());
        CartItem cartItem = prevCartItems.get(0);
        int prevAmount = cartItem.getAmount();
        String type = "plus";
        cartItemService.renewCartItemsQuantity(cartItem.getId(), type);
        assertThat(cartItem.getAmount()).isSameAs(prevAmount + 1);
    }
}