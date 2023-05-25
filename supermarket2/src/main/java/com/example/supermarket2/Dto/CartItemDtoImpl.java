package com.example.supermarket2.Dto;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Repository;

import com.example.supermarket2.models.CartItem;
import com.example.supermarket2.models.Product;
import com.example.supermarket2.models.User;

@Repository
public class CartItemDtoImpl implements CartItemDto {

    @Override
    public CartItem createItem(@AuthenticationPrincipal User user, Product product, int quantity) {
        CartItem cartItem = new CartItem();
        cartItem.setProduct(product);
        cartItem.setQuantity(quantity);
        cartItem.setUserID(user.getId());

        int price = quantity * product.getPrice();
        cartItem.setPrice(price);

        return cartItem;  
    }
    
}
