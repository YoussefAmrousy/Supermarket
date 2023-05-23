package com.example.supermarket2.Dto;

import com.example.supermarket2.models.CartItem;
import com.example.supermarket2.models.Product;

public interface ProductDto {
    public void increaseProductQuantity(Product product, CartItem cartItem);
    public void decreaseProductQuantity(Product product, CartItem cartItem, int quantity);
}
