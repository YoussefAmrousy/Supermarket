package com.example.supermarket2.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.supermarket2.Dto.CartDto;
import com.example.supermarket2.Dto.CartItemDto;
import com.example.supermarket2.Dto.ProductDto;
import com.example.supermarket2.models.Cart;
import com.example.supermarket2.models.CartItem;
import com.example.supermarket2.models.Product;
import com.example.supermarket2.models.User;
import com.example.supermarket2.repositories.CartItemRepo;
import com.example.supermarket2.repositories.CartRepo;
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

    @Autowired
    private ProductDto productDto;

    @Autowired
    private CartItemDto cartItemDto;

    @Autowired
    private CartRepo cartRepo;

    @PostMapping("save-product-cart")
    public String saveProductCart(@RequestParam(name = "id", required = true) Long id,
            @RequestParam(name = "quan", required = true) int quantity,
            @AuthenticationPrincipal User user, RedirectAttributes redirectAttributes) {
                
                Product product = this.productRepo.findById(id).orElse(null); // check if item exists
                
                // Check if product exists
                if (product == null) {
                    redirectAttributes.addFlashAttribute("message", "Product not found!");
                    return "redirect:/supermarket/homepage";
                }

                // Check if there is enough quantity
                if (quantity > product.getQuantity()) {
                    redirectAttributes.addFlashAttribute("message", "Not enough quantity available, there's only " + product.getQuantity() + " available");
                    return "redirect:/supermarket/homepage";
                }
                

                // Check if there is cart created or cart is already ordered
                List<Cart> carts = this.cartRepo.findAllByuserID(user.getId());
                Cart cart = null;
                if (!carts.isEmpty()) {
                    for (Cart cartt: carts) {
                            if (cartt.isOrdered()) {
                                continue;
                            }
                            cart = cartt;
                            break;
                    }
                }

                if (cart == null) {
                    cart = new Cart();
                    cart.setUserID(user.getId());
                    cartRepo.save(cart);
                }

                // Create CartItem
                CartItem cartItem = cartItemDto.createItem(user, product, quantity, cart);
                cartItemRepo.save(cartItem);

                // Deduct quantity from product quantity
                productDto.decreaseProductQuantity(product, cartItem, quantity);

                // Add cartItem to user cart
                cartDto.saveCartItemToCart(user, cart, cartItem);
                cartRepo.save(cart);
                cartItemRepo.save(cartItem);

                cartDto.increaseCartSubTotal(user, cart, cartItem.getPrice());
                cartRepo.save(cart);

                redirectAttributes.addFlashAttribute("message", "You've added " + product.getName() + " to your cart");

                return "redirect:/supermarket/homepage";
    }

    @PostMapping("/deleteItem")
    public String deleteCartItem(@RequestParam(name = "id", required = true) Long id,
            @AuthenticationPrincipal User user, RedirectAttributes redirectAttributes) {
        CartItem cartItem = this.cartItemRepo.findById(id).orElse(null);
        if (cartItem != null) {
            Product product = cartItem.getProduct();
            productDto.increaseProductQuantity(product, cartItem);

            cartDto.deleteCartItemFromCart(user, cartItem);
            cartItemRepo.delete(cartItem);
        }

        return "redirect:/supermarket/viewCart";
    }

}
