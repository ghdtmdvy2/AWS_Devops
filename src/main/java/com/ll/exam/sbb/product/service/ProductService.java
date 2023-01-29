package com.ll.exam.sbb.product.service;

import com.ll.exam.sbb.product.entity.Product;
import com.ll.exam.sbb.product.repository.ProductRepository;
import com.ll.exam.sbb.user.entity.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public Product create(String subject, String content, int price, SiteUser user){
        Product product = new Product();
        product.setAuthor(user);
        product.setContent(content);
        product.setSubject(subject);
        product.setPrice(price);
        productRepository.save(product);
        return product;
    }

    public List<Product> getProducts(SiteUser user) {
        return productRepository.findByAuthorId(user.getId());
    }

    public Optional<Product> getProduct(Product product) {
        return productRepository.findById(product.getId());
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
}
