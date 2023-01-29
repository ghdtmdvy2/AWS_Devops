package com.example.driveanalysis.order.service;

import com.example.driveanalysis.cartitem.entity.CartItem;
import com.example.driveanalysis.cartitem.repository.CartItemRepository;
import com.example.driveanalysis.order.entity.OrderItem;
import com.example.driveanalysis.order.entity.ProductOrder;
import com.example.driveanalysis.order.repository.ProductOrderRepository;
import com.example.driveanalysis.product.entity.Product;
import com.example.driveanalysis.product.repository.ProductRepository;
import com.example.driveanalysis.user.entity.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductOrderService {
    private final ProductOrderRepository productOrderRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    public ProductOrder createFromCartProductOrder(SiteUser orderer){

        List<CartItem> cartItems = cartItemRepository.findAllByBuyerId(orderer.getId());
        List<OrderItem> orderItems = new ArrayList<>();

        for (CartItem cartItem : cartItems) {
            Product product = cartItem.getProduct();
            int productAmount = cartItem.getAmount();
            if (product.isOrderable() && product.getStock() - productAmount > 0){
                product.setStock(product.getStock() - productAmount);
                orderItems.add(new OrderItem(product));
                productRepository.save(product);
            }
        }
        cartItemRepository.deleteAll(cartItems);

        ProductOrder productOrder = new ProductOrder();
        productOrder.setOrderer(orderer);
        productOrder.setCanceled(false);
        productOrder.setPaid(false);
        productOrder.setPayDate(LocalDateTime.now());
        productOrder.setReadyStatus(false);
        productOrder.setRefunded(false);
        productOrder.setOrderItems(orderItems);
        productOrderRepository.save(productOrder);
        return productOrder;
    }
}
