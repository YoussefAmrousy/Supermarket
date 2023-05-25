package com.example.supermarket2.Dao;

import com.example.supermarket2.models.User;

public interface UserDao {

    public User getUserByName(String name);
    public User getUserById(Long id);
    
    public boolean updateUsername(String oldUsername, String newUsername);
    public boolean updatePassword(Long userID, String newPassword);

    public boolean updateAddress(Long userID, String address);
    public boolean updateCreditCard(Long userID, Long creditCard);
    public boolean updateCvv(Long userID,int cvv);
    }
