package com.example.supermarket2.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.supermarket2.models.CartItem;

public interface CartItemRepo extends JpaRepository<CartItem, Long> {
    public Optional<CartItem> findByUserID(Long id);
}
