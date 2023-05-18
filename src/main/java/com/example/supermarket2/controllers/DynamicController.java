package com.example.supermarket2.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/supermarket")
public class DynamicController {

    @GetMapping("/homepage")
    public ModelAndView getHomePage() {
        ModelAndView mav = new ModelAndView("homepage.html");
        return mav;
    }
}
