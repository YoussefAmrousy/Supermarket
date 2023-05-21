package com.example.supermarket2.Dto;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;

import com.example.supermarket2.models.CartItem;
import com.example.supermarket2.models.User;

public interface CartDto {
    public void saveCartItemToCart(@AuthenticationPrincipal User user, CartItem cartItem);
    public List<CartItem> getCartItemsFromCart(@AuthenticationPrincipal User user);
}
