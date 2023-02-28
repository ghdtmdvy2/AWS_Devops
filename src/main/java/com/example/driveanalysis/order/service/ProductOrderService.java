package com.example.driveanalysis.order.service;

import com.example.driveanalysis.base.exception.DataNotFoundException;
import com.example.driveanalysis.cartitem.entity.CartItem;
import com.example.driveanalysis.cartitem.repository.CartItemRepository;
import com.example.driveanalysis.cashlog.service.CashLogService;
import com.example.driveanalysis.order.entity.OrderItem;
import com.example.driveanalysis.order.entity.ProductOrder;
import com.example.driveanalysis.order.repository.OrderItemRepository;
import com.example.driveanalysis.order.repository.ProductOrderRepository;
import com.example.driveanalysis.product.entity.Product;
import com.example.driveanalysis.user.entity.SiteUser;
import com.example.driveanalysis.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductOrderService {
    private final ProductOrderRepository productOrderRepository;
    private final CartItemRepository cartItemRepository;
    private final CashLogService cashLogService;
    private final UserService userService;
    private final OrderItemRepository orderItemRepository;
    @Transactional
    public ProductOrder createFromCartProductOrder(SiteUser orderer){

        List<CartItem> cartItems = cartItemRepository.findAllByBuyerId(orderer.getId());
        List<OrderItem> orderItems = new ArrayList<>();

        for (CartItem cartItem : cartItems) {
            Product product = cartItem.getProduct();
            int productAmount = cartItem.getAmount();
            if (product.isOrderable() && product.getStock() - productAmount > 0){
                orderItems.add(new OrderItem(product, productAmount));
            }
        }
        cartItemRepository.deleteAll(cartItems);

        ProductOrder productOrder = new ProductOrder();
        productOrder.setOrderer(orderer);
        productOrder.addOrderItem(orderItems);
        productOrder.makeName();
        productOrderRepository.save(productOrder);
        return productOrder;
    }

    public ProductOrder findProductOrder(long orderId) {
        return productOrderRepository.findById(orderId).orElseThrow(() -> new DataNotFoundException("order not found"));
    }
    @Transactional
    public void payTossPayments(ProductOrder productOrder){
        int pgPay = productOrder.calculatePay();
        cashLogService.addCash(productOrder.getOrderer(),pgPay,"주문__%d__충전__토스페이먼츠".formatted(productOrder.getId()));
        cashLogService.addCash(productOrder.getOrderer(),pgPay * -1,"주문__%d__사용__토스페이먼츠".formatted(productOrder.getId()));
        SiteUser orderer = productOrder.getOrderer();
        productOrder.setPaymentDone();
        productOrderRepository.save(productOrder);
        userService.setProductPayTrue(orderer);
    }

    public List<ProductOrder> findProductOrders(long userId) {
        return productOrderRepository.findByIsPaidTrueAndOrdererId(userId);
    }

    public void deleteOrderItemByProductId(long productId) {
        orderItemRepository.deleteAllByProductId(productId);
    }
}
