package com.example.driveanalysis.product.controller;

import com.example.driveanalysis.order.service.ProductOrderService;
import com.example.driveanalysis.product.dto.ProductForm;
import com.example.driveanalysis.product.entity.Product;
import com.example.driveanalysis.product.service.ProductService;
import com.example.driveanalysis.user.dto.UserContext;
import com.example.driveanalysis.user.entity.SiteUser;
import com.example.driveanalysis.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final ProductOrderService productOrderService;

    private final UserService userService;
    @GetMapping("/")
    public String showProductList(Model model, @RequestParam(defaultValue = "0") int page){
        Page<Product> productList = productService.getAllProduct(page);
        model.addAttribute("productList",productList);
        return "product/product_list";
    }

    @GetMapping("/{productId}")
    public String showProduct(Model model, @PathVariable long productId){
        Product product = productService.getProduct(productId);
        model.addAttribute("product",product);
        return "product/product_detail";
    }
    @GetMapping(value = "/", params = "redirectURL")
    public String showCreateProduct(ProductForm productForm){
        return "product/product_form";
    }
    @PostMapping("/")
    public String createProduct(@Valid ProductForm productForm, @AuthenticationPrincipal UserContext userContext){
        SiteUser user = userService.getUser(userContext.getUsername());
        productService.create(productForm.getSubject(),productForm.getContent(), productForm.getPrice(), user,productForm.getStock());
        return "redirect:/product/";
    }

    @DeleteMapping("/{productId}")
    public String deleteProduct(@PathVariable long productId, @AuthenticationPrincipal UserContext userContext){
        productOrderService.deleteOrderItemByProductId(productId);
        productService.delete(productId);
        return "redirect:/product/";
    }
}
