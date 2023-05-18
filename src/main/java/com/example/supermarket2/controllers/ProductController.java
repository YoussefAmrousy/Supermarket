package com.example.supermarket2.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.supermarket2.models.Product;
import com.example.supermarket2.repositories.ProductRepo;

@Controller
@RequestMapping("/supermarket")
public class ProductController {

    @Autowired
    private ProductRepo productRepo;

    @GetMapping("/addProduct-form")
    public ModelAndView getProductForm() {
        ModelAndView mav = new ModelAndView("add-product.html");
        Product product = new Product();
        mav.addObject("product", product);
        return mav;
    }

    @PostMapping("add-product")
    public String saveProduct(@ModelAttribute Product product) {
        this.productRepo.save(product);
        return "redirect:/supermarket/viewProducts-form";
    }
}
