package com.example.supermarket2.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.supermarket2.models.Order;
import com.example.supermarket2.repositories.OrderRepo;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private OrderRepo orderRepo;

    @GetMapping("/viewAllOrders")
    public ModelAndView viewOrders() {
        ModelAndView mav = new ModelAndView("view-all-orders.html");
        List<Order> orders = orderRepo.findAll();
        mav.addObject("orders", orders);
        return mav;
    }
}
