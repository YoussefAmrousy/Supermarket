package com.example.supermarket2.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;

    private int quantity;
    private Long userID;
    private int price;

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public CartItem() {
    }

    public CartItem(Long id, Product product, int quantity, Long userID, int price) {
        this.id = id;
        this.product = product;
        this.quantity = quantity;
        this.userID = userID;
        this.price = price;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Product getProduct() {
        return this.product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Long getUserID() {
        return this.userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public int getPrice() {
        return this.price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}