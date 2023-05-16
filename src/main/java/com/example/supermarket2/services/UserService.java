package com.example.supermarket2.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.supermarket2.models.User;
import com.example.supermarket2.repositories.UserRepo;

@Service
public class UserService implements UserDetailsService { 

    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = this.userRepo.findByEmail(email).orElse(null);
        return user;
    }

}
