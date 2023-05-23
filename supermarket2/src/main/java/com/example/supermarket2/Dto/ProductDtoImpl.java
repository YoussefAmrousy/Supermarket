package com.example.supermarket2.Dto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.supermarket2.models.CartItem;
import com.example.supermarket2.models.Product;
import com.example.supermarket2.repositories.ProductRepo;

@Repository
public class ProductDtoImpl implements ProductDto {

    @Autowired
    private ProductRepo productRepo;
    
    @Override
    public void increaseProductQuantity(Product product, CartItem cartItem) {
        product.setQuantity(product.getQuantity() + cartItem.getQuantity());
        productRepo.save(product);
    }

    @Override
    public void decreaseProductQuantity(Product product, CartItem cartItem, int quantity) {
        product.setQuantity(product.getQuantity() - quantity);
        productRepo.save(product);
    }

}
