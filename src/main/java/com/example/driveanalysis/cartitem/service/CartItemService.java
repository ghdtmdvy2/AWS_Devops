package com.example.driveanalysis.cartitem.service;

import com.example.driveanalysis.cartitem.entity.CartItem;
import com.example.driveanalysis.cartitem.repository.CartItemRepository;
import com.example.driveanalysis.product.entity.Product;
import com.example.driveanalysis.user.entity.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartItemService {
    private final CartItemRepository cartItemRepository;

    public CartItem addCartItem(Product product, SiteUser buyer) {
        CartItem oldCartItem = cartItemRepository.findByProductIdAndBuyerId(product.getId(), buyer.getId());

        if (oldCartItem != null) {
            oldCartItem.setAmount(oldCartItem.getAmount() + 1);
            cartItemRepository.save(oldCartItem);
            return oldCartItem;
        }

        CartItem cartItem = new CartItem();
        cartItem.setAmount(1);
        cartItem.setBuyer(buyer);
        cartItem.setProduct(product);
        cartItemRepository.save(cartItem);

        return cartItem;
    }

    public List<CartItem> findCartItems(SiteUser user) {
        return cartItemRepository.findAllByBuyerId(user.getId());
    }
}
