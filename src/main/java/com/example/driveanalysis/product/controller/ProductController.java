package com.example.driveanalysis.product.controller;

import com.example.driveanalysis.base.config.UserContext;
import com.example.driveanalysis.product.dto.ProductForm;
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

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    private final UserService userService;
    @GetMapping("/list")
    public String showProductList(Model model){
        List<Product> productList = productService.getAllProduct();
        model.addAttribute("productList",productList);
        return "product/product_list";
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/create")
    public String showCreateProduct(){
        return "product/product_form";
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/create")
    public String createProduct(@Valid ProductForm productForm, @AuthenticationPrincipal UserContext userContext){
        SiteUser user = userService.getUser(userContext.getUsername());
        productService.create(productForm.getSubject(),productForm.getContent(), productForm.getPrice(), user,productForm.getStock());
        return "redirect:/product/list";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/delete")
    public String deleteProduct(long productId, @AuthenticationPrincipal UserContext userContext){
        productService.delete(productId);
        return "redirect:/product/list";
    }
}
