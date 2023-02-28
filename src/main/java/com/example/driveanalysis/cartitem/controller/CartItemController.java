package com.example.driveanalysis.cartitem.controller;

import com.example.driveanalysis.cartitem.entity.CartItem;
import com.example.driveanalysis.cartitem.service.CartItemService;
import com.example.driveanalysis.product.entity.Product;
import com.example.driveanalysis.product.service.ProductService;
import com.example.driveanalysis.user.dto.UserContext;
import com.example.driveanalysis.user.entity.SiteUser;
import com.example.driveanalysis.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/cartItem")
public class CartItemController {
    private final CartItemService cartItemService;
    private final UserService userService;
    private final ProductService productService;
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/")
    public String showCartItem(@AuthenticationPrincipal UserContext userContext, Model model){
        List<CartItem> cartItems = cartItemService.findCartItems(userContext.getId());
        model.addAttribute("cartItems",cartItems);
        return "cartItem/cartItem_list";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/{productId}")
    public String createCartItem(@AuthenticationPrincipal UserContext userContext, @PathVariable long productId, @RequestParam(defaultValue = "1") int amount){
        SiteUser user = userService.getUser(userContext.getUsername());
        Product product = productService.getProduct(productId);
        cartItemService.addCartItem(product,user, amount);
        return "redirect:/product/";
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/")
    public String removeItems(String ids, String amounts){
        String[] idBits = ids.split(",");
        for (String cartItemId : idBits){
            cartItemService.deleteCartItem(Long.parseLong(cartItemId));
        }
        return "redirect:/cartItem/";
    }

    @PreAuthorize("isAuthenticated()")
    @PatchMapping("/{cartItemId}")
    @ResponseBody
    public void renewQuantityCartItem(@PathVariable long cartItemId, String type){
        cartItemService.renewCartItemsQuantity(cartItemId,type);
    }
}
