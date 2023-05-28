package com.example.supermarket2.controllers;

import com.example.supermarket2.models.Cart;
import com.example.supermarket2.models.CartItem;
import com.example.supermarket2.models.Product;
import com.example.supermarket2.repositories.CartItemRepo;
import com.example.supermarket2.repositories.CartRepo;
import com.example.supermarket2.repositories.ProductRepo;
import com.example.supermarket2.Dto.ProductDto;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/supermarket")
public class ProductController {

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private ProductDto productDto;

    @Autowired
    private CartItemRepo cartItemRepo;

    @GetMapping("/addProduct-form")
    public ModelAndView getProductForm() {
        ModelAndView mav = new ModelAndView("add-product.html");
        Product product = new Product();
        mav.addObject("product", product);
        return mav;
    }

    @PostMapping("/add-product")
    public String saveProduct(@ModelAttribute Product product) {
        this.productRepo.save(product);
        return "redirect:/supermarket/viewProducts-form";
    }

    @GetMapping("/viewProducts-form")
    public ModelAndView getViewForm(Model model) {
        ModelAndView mav = new ModelAndView("view-products.html");
        mav.addObject("products", productRepo.findAll());
        return mav;
    }

    @PostMapping("/update-quantity")
    public String updateQuantity(@RequestParam Long productId, @RequestParam Integer newQuantity,
                                 RedirectAttributes redirectAttributes) {
        boolean success = productDto.updateQuantity(productId, newQuantity);
        if (success) {
            redirectAttributes.addFlashAttribute("message", "Quantity updated successfully");
        } else {
            redirectAttributes.addFlashAttribute("error", "Failed to update quantity");
        }
        return "redirect:/supermarket/viewProducts-form";
    }

    @PostMapping("/delete-product")
    public String deleteProduct(@RequestParam Long productId, RedirectAttributes redirectAttributes) {
        Product product = productRepo.findById(productId).orElse(null);
        List<CartItem> cartItems = cartItemRepo.findAll();
        for (CartItem cartItem : cartItems) {
            if (cartItem.getProduct().getProductId() == productId) {
                cartItemRepo.delete(cartItem);
            }
        }
        if (product != null) {
            productRepo.delete(product);
            redirectAttributes.addFlashAttribute("message", "Product Deleted Successfully!");
        }
        return "redirect:/supermarket/viewProducts-form"; 
    }
}
