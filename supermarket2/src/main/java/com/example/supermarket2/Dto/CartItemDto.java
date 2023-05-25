package com.example.supermarket2.Dto;

import org.springframework.security.core.annotation.AuthenticationPrincipal;

import com.example.supermarket2.models.Cart;
import com.example.supermarket2.models.CartItem;
import com.example.supermarket2.models.Product;
import com.example.supermarket2.models.User;

public interface CartItemDto {
    public CartItem createItem(@AuthenticationPrincipal User user, Product product, int quantity, Cart cart);
}
