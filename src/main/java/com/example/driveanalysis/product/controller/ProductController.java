package com.example.driveanalysis.product.controller;

import com.example.driveanalysis.product.entity.Product;
import com.example.driveanalysis.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@PreAuthorize("hasRole('ROLE_ADMIN')")
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    @GetMapping("/list")
    public String showProductList(Model model){
        List<Product> productList = productService.getAllProduct();
        model.addAttribute("productList",productList);
        return "product/product_list";
    }
}
