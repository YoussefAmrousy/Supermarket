package com.example.supermarket2.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.supermarket2.models.Cart;

public interface CartRepo extends JpaRepository<Cart, Long> {
    public Optional<Cart> findByuserID(Long userID);
    
}
