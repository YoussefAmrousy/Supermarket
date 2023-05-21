package com.example.supermarket2.models;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;


@Entity
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long CartId;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItem> cartItems = new ArrayList<>();
    
    private Long userID;
    private int subtotal;


    public Cart() {
    }

    public Cart(Long CartId, List<CartItem> cartItems, Long userID, int subtotal) {
        this.CartId = CartId;
        this.cartItems = cartItems;
        this.userID = userID;
        this.subtotal = subtotal;
    }

    public Long getCartId() {
        return this.CartId;
    }

    public void setCartId(Long CartId) {
        this.CartId = CartId;
    }

    public List<CartItem> getCartItems() {
        return this.cartItems;
    }

    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
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

    public void addCartItem(CartItem cartItem) {
        cartItems.add(cartItem);
        cartItem.setCart(this);
    }

    public void removeCartItem(CartItem cartItem) {
        cartItems.remove(cartItem);
        cartItem.setCart(null);
    }
    
}