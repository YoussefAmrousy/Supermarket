package com.example.supermarket2.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.supermarket2.models.Order;

@Repository
public interface OrderRepo extends JpaRepository<Order, Long> {
    public Optional<Order> findByUserID(Long id);        
}
