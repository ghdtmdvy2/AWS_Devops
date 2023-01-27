package com.ll.exam.sbb.product.service;

import com.ll.exam.sbb.product.entity.Product;
import com.ll.exam.sbb.product.repository.ProductRepository;
import com.ll.exam.sbb.user.entity.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
