package com.example.driveanalysis.cartitem.controller;

import com.example.driveanalysis.base.config.UserContext;
import com.example.driveanalysis.cartitem.entity.CartItem;
import com.example.driveanalysis.cartitem.service.CartItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/cartItem")
public class CartItemController {
    private final CartItemService cartItemService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/list")
    public String showCartItem(@AuthenticationPrincipal UserContext userContext, Model model){
        List<CartItem> cartItems = cartItemService.findCartItems(userContext.getId());
        model.addAttribute("cartItems",cartItems);
        return "cartItem/cartItem_list";
    }
}
