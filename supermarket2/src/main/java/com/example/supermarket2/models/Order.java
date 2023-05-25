package com.example.supermarket2.models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "OrderPlaced")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "cart_id", nullable = false)
    private Cart cart;

    private Long userID;
    private int subtotal;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date dateOrdered;

    public Order() {
    }

    public Order(Long id, Cart cart, Long userID, int subtotal, Date dateOrdered) {
        this.id = id;
        this.cart = cart;
        this.userID = userID;
        this.subtotal = subtotal;
        this.dateOrdered = dateOrdered;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Cart getCart() {
        return this.cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
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

    public Date getDateOrdered() {
        return this.dateOrdered;
    }

    public void setDateOrdered(Date dateOrdered) {
        this.dateOrdered = dateOrdered;
    }
}