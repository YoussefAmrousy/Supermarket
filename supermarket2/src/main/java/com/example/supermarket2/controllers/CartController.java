package com.example.supermarket2.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.supermarket2.models.Cart;
import com.example.supermarket2.models.User;
import com.example.supermarket2.repositories.CartRepo;

@Controller
@RequestMapping("/supermarket")
public class CartController {


    @Autowired
    private CartRepo cartRepo;


    @GetMapping("/viewCart")
    public ModelAndView viewCart(@AuthenticationPrincipal User user) {
        ModelAndView mav = new ModelAndView("view-cart.html");
        List<Cart> carts = cartRepo.findAllByuserID(user.getId());
        for (Cart cart : carts) {
            if (cart.isOrdered() != true) {
                mav.addObject("cartItems", cart.getCartItems());
                break;
            }
        }
        return mav;
    }
    
}