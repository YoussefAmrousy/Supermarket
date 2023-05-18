package com.example.supermarket2.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.supermarket2.models.User;

public interface UserRepo extends JpaRepository<User, String> {

    public Optional<User> findByEmail(String email);

}