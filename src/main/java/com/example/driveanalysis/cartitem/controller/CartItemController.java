package com.example.driveanalysis.cartitem.controller;

import com.example.driveanalysis.base.config.UserContext;
import com.example.driveanalysis.cartitem.entity.CartItem;
import com.example.driveanalysis.cartitem.service.CartItemService;
import com.example.driveanalysis.product.entity.Product;
import com.example.driveanalysis.product.service.ProductService;
import com.example.driveanalysis.user.entity.SiteUser;
import com.example.driveanalysis.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/cartItem")
public class CartItemController {
    private final CartItemService cartItemService;
    private final UserService userService;
    private final ProductService productService;
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/list")
    public String showCartItem(@AuthenticationPrincipal UserContext userContext, Model model){
        List<CartItem> cartItems = cartItemService.findCartItems(userContext.getId());
        model.addAttribute("cartItems",cartItems);
        return "cartItem/cartItem_list";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create")
    public String createCartItem(@AuthenticationPrincipal UserContext userContext, long productId, int amount){
        SiteUser user = userService.getUser(userContext.getUsername());
        Product product = productService.getProduct(productId);
        cartItemService.addCartItem(product,user, amount);
        return "redirect:/";
    }
}
