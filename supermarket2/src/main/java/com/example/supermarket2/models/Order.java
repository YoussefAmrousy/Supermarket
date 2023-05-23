package com.example.supermarket2.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userID;
    private int subtotal;


    public Order() {
    }

    public Order(Long id, Long userID, int subtotal) {
        this.id = id;
        this.userID = userID;
        this.subtotal = subtotal;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserID() {
        return this.userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public int getSubtotal() {
        return this.subtotal;
    }

    public void setSubtotal(int subtotal) {
        this.subtotal = subtotal;
    }
    
}