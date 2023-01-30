package com.example.driveanalysis.product.service;

import com.example.driveanalysis.base.exception.DataNotFoundException;
import com.example.driveanalysis.user.entity.SiteUser;
import com.example.driveanalysis.product.entity.Product;
import com.example.driveanalysis.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public Product create(String subject, String content, int price, SiteUser user, int stock){
        Product product = new Product();
        product.setAuthor(user);
        product.setContent(content);
        product.setSubject(subject);
        product.setPrice(price);
        product.setStock(stock);
        product.setOrderable(true);
        productRepository.save(product);
        return product;
    }

    public List<Product> getUserProducts(SiteUser user) {
        return productRepository.findByAuthorId(user.getId());
    }

    public Product getProduct(long productId) {
        return productRepository.findById(productId).orElseThrow(() -> new DataNotFoundException("product not found"));
    }

    public Product modify(Product product, String subject, String content, int price) {
        product.setSubject(subject);
        product.setContent(content);
        product.setPrice(price);
        productRepository.save(product);
        return product;
    }

    public void delete(Product product) {
        productRepository.delete(product);
    }

    public List<Product> getAllProduct() {
        return productRepository.findAll();
    }
}
