package com.example.supermarket2.Dto;

import org.springframework.security.core.annotation.AuthenticationPrincipal;

import com.example.supermarket2.models.Cart;
import com.example.supermarket2.models.User;

public interface CartItemDto {
    public void setCartSubTotal(@AuthenticationPrincipal User user, Cart cart);
}
