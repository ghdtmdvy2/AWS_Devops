package com.example.driveanalysis.product.service;

import com.example.driveanalysis.base.exception.DataNotFoundException;
import com.example.driveanalysis.order.service.ProductOrderService;
import com.example.driveanalysis.user.entity.SiteUser;
import com.example.driveanalysis.product.entity.Product;
import com.example.driveanalysis.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {
    private final ProductRepository productRepository;

    @Transactional
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

    @Transactional
    public Product modify(Product product, String subject, String content, int price) {
        product.setSubject(subject);
        product.setContent(content);
        product.setPrice(price);
        productRepository.save(product);
        return product;
    }

    @Transactional
    public void delete(long productId) {
        productRepository.deleteById(productId);
    }

    public Page<Product> getAllProduct(int page) {
        List<Sort.Order> sorts = new ArrayList<>();

        sorts.add(Sort.Order.desc("id")); // 최신순

        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts)); // 한 페이지에 10까지 가능

        return productRepository.findAll(pageable);
    }
}
