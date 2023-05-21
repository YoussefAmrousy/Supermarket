package com.example.supermarket2.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.supermarket2.models.Product;

public interface ProductRepo extends JpaRepository<Product, Long> {
    public Optional<Product> findByName(String name);
    public Optional<Product> findById(Long id);
}
