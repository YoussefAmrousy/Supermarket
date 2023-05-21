package com.example.supermarket2.Dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Repository;

import com.example.supermarket2.models.Cart;
import com.example.supermarket2.models.CartItem;
import com.example.supermarket2.models.User;
import com.example.supermarket2.repositories.CartItemRepo;
import com.example.supermarket2.repositories.CartRepo;

@Repository
public class CartDtoImpl implements CartDto {


    @Autowired
    private CartRepo cartRepo;

    @Autowired
    private CartItemRepo cartItemRepo;

    @Override
    public void saveCartItemToCart(@AuthenticationPrincipal User user, CartItem cartItem) {
        Cart cart = cartRepo.findByuserID(user.getId()).orElse(null);
        if (cart == null) {
            cart = new Cart();
            // cart.setSubtotal(0);
            cart.setUserID(user.getId());
            cartRepo.save(cart);
        }
        cartItem.setCart(cart);
        cartItemRepo.save(cartItem);
        cart.addCartItem(cartItem);
        cartRepo.save(cart);
    }

    @Override
    public List<CartItem> getCartItemsFromCart(@AuthenticationPrincipal User user) {
        Cart cart= cartRepo.findByuserID(user.getId()).orElse(null);
        if (cart == null) {
            return null;
        }
        else {
            return new ArrayList<>(cart.getCartItems());
        }
    }

}
