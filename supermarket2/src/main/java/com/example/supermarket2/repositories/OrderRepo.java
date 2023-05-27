package com.example.supermarket2.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.supermarket2.models.Order;

public interface OrderRepo extends JpaRepository<Order, Long> {
    public Optional<Order> findByUserID(Long id);

    public List<Order> findAllByuserID(Long id);

    List<Order> findAllByuserIDOrderByDateOrderedDesc(Long userId);

}
