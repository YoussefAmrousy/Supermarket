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
    public void saveCartItemToCart(@AuthenticationPrincipal User user, Cart cart, CartItem cartItem) {
        cartItem.setCart(cart);
        cartItemRepo.save(cartItem);

        cart.addCartItem(cartItem);
    }

    @Override
    public List<CartItem> getCartItemsFromCart(@AuthenticationPrincipal User user) {
        Cart cart = cartRepo.findByuserID(user.getId()).orElse(null);
        if (cart == null) {
            return null;
        } else if (cart != null && cart.isOrdered() == false) {
            return new ArrayList<>(cart.getCartItems());
        }
        return null;
    }

    @Override
    public void deleteCartItemFromCart(User user, CartItem cartItem) {
        // Cart cart = cartRepo.findByuserID(user.getId()).orElse(null);
        Cart cart = null;
        List<Cart> carts = cartRepo.findAllByuserID(user.getId());
        for (Cart cartt : carts) {
            if (cartt.isOrdered() != true) {
                cart = cartt;
                break;
            }
        }

        if (cart != null && cart.isOrdered() == false) {
            cart.getCartItems().remove(cartItem);
            decreaseCartSubTotal(user, cart, cartItem.getPrice());
            
            if (cart.getCartItems().isEmpty()) {
                cartRepo.delete(cart);
                cartRepo.save(cart);
            }
            cartRepo.save(cart);
        }
    }

    @Override
    public void increaseCartSubTotal(@AuthenticationPrincipal User user, Cart cart, int price) {
        int subtotal = cart.getSubtotal() + price;
        cart.setSubtotal(subtotal);
    }

    @Override
    public void decreaseCartSubTotal(User user, Cart cart, int price) {
        int subtotal = cart.getSubtotal() - price;
        cart.setSubtotal(subtotal);
    }

}
