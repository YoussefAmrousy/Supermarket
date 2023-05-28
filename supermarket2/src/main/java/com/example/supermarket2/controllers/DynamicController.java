package com.example.supermarket2.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.supermarket2.models.User;
import com.example.supermarket2.repositories.ProductRepo;

@Controller
@RequestMapping("/supermarket")
public class DynamicController {

    @Autowired
    private ProductRepo productRepo;
    @GetMapping("/homepage")
    public ModelAndView getHomePage() {
        ModelAndView mav = new ModelAndView("homepage.html");
        mav.addObject("products", productRepo.findAll());
        return mav;
    }
    @GetMapping("/adminHomepage")
    public ModelAndView getAdminPage(@AuthenticationPrincipal User user) {
        ModelAndView mav = new ModelAndView("admin-home-page.html");
        mav.addObject("user", user);
        return mav;
    }
}
