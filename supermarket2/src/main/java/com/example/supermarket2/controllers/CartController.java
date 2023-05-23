package com.example.supermarket2.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.supermarket2.Dto.CartDto;
import com.example.supermarket2.models.CartItem;
import com.example.supermarket2.models.User;

@Controller
@RequestMapping("/supermarket")
public class CartController {

    @Autowired
    private CartDto cartDto;


    @GetMapping("/viewCart")
    public ModelAndView viewCart(@AuthenticationPrincipal User user) {
        ModelAndView mav = new ModelAndView("view-cart.html");
        List<CartItem> cartItems = cartDto.getCartItemsFromCart(user);
        mav.addObject("cartItems", cartItems);
        int subtotal = 0;
        for (CartItem cartItem : cartItems) {
            int totalprice = cartItem.getPrice();
            subtotal = subtotal + totalprice;
        }
        mav.addObject("subtotal", subtotal);
        return mav;
    }
    
}