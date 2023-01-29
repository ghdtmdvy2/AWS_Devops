package com.example.driveanalysis.order.service;

import com.example.driveanalysis.order.entity.ProductOrder;
import com.example.driveanalysis.order.repository.ProductOrderRepository;
import com.example.driveanalysis.product.entity.Product;
import com.example.driveanalysis.user.entity.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ProductOrderService {
    private final ProductOrderRepository productOrderRepository;

    public ProductOrder createProductOrder(Product product, SiteUser orderer){
        ProductOrder productOrder = new ProductOrder();
        productOrder.setOrderer(orderer);
        productOrder.setCanceled(false);
        productOrder.setPaid(true);
        productOrder.setPayDate(LocalDateTime.now());
        productOrder.setReadyStatus(true);
        productOrder.setRefunded(false);
        productOrderRepository.save(productOrder);
        return productOrder;
    }
}
