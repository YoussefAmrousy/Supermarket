package com.example.supermarket2.Dto;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Repository;

import com.example.supermarket2.models.Cart;
import com.example.supermarket2.models.CartItem;
import com.example.supermarket2.models.User;

@Repository
public class CartItemDtoImpl implements CartItemDto {

    @Autowired
    private CartDto cartDto;

    @Override
    public void setCartSubTotal(@AuthenticationPrincipal User user, Cart cart) {
        List<CartItem> cartItems = cartDto.getCartItemsFromCart(user);
        int subtotal = 0;
        for (CartItem cartItem : cartItems) {
            int totalprice = cartItem.getPrice();
            subtotal = subtotal + totalprice;
        }
        cart.setSubtotal(subtotal);
    }
    
}
