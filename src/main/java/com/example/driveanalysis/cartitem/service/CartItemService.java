package com.example.driveanalysis.cartitem.service;

import com.example.driveanalysis.base.exception.DataNotFoundException;
import com.example.driveanalysis.cartitem.entity.CartItem;
import com.example.driveanalysis.cartitem.repository.CartItemRepository;
import com.example.driveanalysis.product.entity.Product;
import com.example.driveanalysis.user.entity.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CartItemService {
    private final CartItemRepository cartItemRepository;

    @Transactional
    public CartItem addCartItem(Product product, SiteUser buyer, int amount) {
        CartItem oldCartItem = cartItemRepository.findByProductIdAndBuyerId(product.getId(), buyer.getId());

        if (oldCartItem != null) {
            oldCartItem.setAmount(oldCartItem.getAmount() + amount);
            cartItemRepository.save(oldCartItem);
            return oldCartItem;
        }

        CartItem cartItem = new CartItem();
        cartItem.setAmount(amount);
        cartItem.setBuyer(buyer);
        cartItem.setProduct(product);
        cartItemRepository.save(cartItem);

        return cartItem;
    }


    public List<CartItem> findCartItems(long userId) {
        return cartItemRepository.findAllByBuyerId(userId);
    }
    @Transactional
    public void deleteCartItem(long cartItemId) {
        cartItemRepository.deleteById(cartItemId);
    }
    @Transactional
    public void renewCartItemsQuantity(long cartItemId, String type) {
        CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(() -> new DataNotFoundException("no %d cartItem not found,".formatted(cartItemId)));
        if (type.equals("plus")) {
            cartItem.setAmount(cartItem.getAmount() + 1);
        } else {
            cartItem.setAmount(cartItem.getAmount() - 1);
        }
        cartItemRepository.save(cartItem);
    }
}
