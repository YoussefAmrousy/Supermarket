package com.example.supermarket2.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.supermarket2.Dto.CartDto;
import com.example.supermarket2.models.CartItem;
import com.example.supermarket2.models.Product;
import com.example.supermarket2.models.User;
import com.example.supermarket2.repositories.CartItemRepo;
import com.example.supermarket2.repositories.ProductRepo;

@Controller
@RequestMapping("/supermarket")
public class CartItemController {
    

    @Autowired
    private CartItemRepo cartItemRepo;

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private CartDto cartDto;

    @PostMapping("save-product-cart")
    public String saveProduct(@RequestParam(name = "id", required = true) Long id,
            @RequestParam(name = "quan", required = true) int quantity,
            @AuthenticationPrincipal User user, RedirectAttributes redirectAttributes) {
        Product product = this.productRepo.findById(id).orElse(null);
        if (product != null) {
            if (quantity <= product.getQuantity()) {
                CartItem cartItem = new CartItem();
                cartItem.setProduct(product);
                cartItem.setQuantity(quantity);
                cartItem.setUserID(user.getId());

                cartItemRepo.save(cartItem);

                product.setQuantity(product.getQuantity() - quantity);
                productRepo.save(product);

                cartDto.saveCartItemToCart(user, cartItem);
                redirectAttributes.addFlashAttribute("message", "");

                return "redirect:/supermarket/homepage";
            } else {
                redirectAttributes.addFlashAttribute("message",
                        "There's only " + product.getQuantity() + " available");
                return "redirect:/supermarket/homepage";
            }
        }
        return null;
    }

    @PostMapping
    public String deleteCartItem(@RequestParam(name = "id", required = true) Long id, @AuthenticationPrincipal User user) {
        CartItem cartItem = cartItemRepo.findById(id).orElse(null);
        cartItemRepo.delete(cartItem);
        return "redirect:/supermarket/homepage"; 
    }

}
