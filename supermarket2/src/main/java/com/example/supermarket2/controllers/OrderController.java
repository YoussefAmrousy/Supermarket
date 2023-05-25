package com.example.supermarket2.controllers;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.supermarket2.models.Cart;
import com.example.supermarket2.models.Order;
import com.example.supermarket2.models.User;
import com.example.supermarket2.repositories.CartRepo;
import com.example.supermarket2.repositories.OrderRepo;

@Controller
@RequestMapping("/supermarket")
public class OrderController {

    @Autowired
    private CartRepo cartRepo;

    @Autowired
    private OrderRepo orderRepo;

    @GetMapping("/viewOrders")
    public ModelAndView viewOrders(@AuthenticationPrincipal User user) {
        ModelAndView mav = new ModelAndView("view-orders.html");
        List<Order> orders = orderRepo.findAllByuserID(user.getId());
        mav.addObject("orders", orders);
        return mav;

    }

    @PostMapping("/place-order")
    public String placeOrder(@AuthenticationPrincipal User user) {
        Date currentDate = new Date();
        
        Cart cart = null;
        List<Cart> carts = cartRepo.findAllByuserID(user.getId());
        for (Cart cartt : carts) {
            if (cartt.isOrdered() != true) {
                cart = cartt;
                break;
            }
        }
        if (cart != null) {
            if (cart.isOrdered() != true) {
                cart.setOrdered(true);
                cartRepo.save(cart);
                Order order = new Order();
                order.setCart(cart);
                order.setSubtotal(cart.getSubtotal());
                order.setUserID(user.getId());
                order.setDateOrdered(currentDate);
                orderRepo.save(order);
            }
        }
        return "redirect:/supermarket/orderDone";
    }

    @GetMapping("orderDone")
    public ModelAndView orderDone(@AuthenticationPrincipal User user,
            RedirectAttributes redirectAttributes) {
        ModelAndView mav = new ModelAndView("orderDone.html");

        String username = user.getUsername();
        mav.addObject("name", username);
        // redirectAttributes.addFlashAttribute("message", "Hello " + username);

        List<Order> userOrders = orderRepo.findAllByuserIDOrderByDateOrderedDesc(user.getId());
        if (!userOrders.isEmpty()) {
            Order lastOrder = userOrders.get(0);
            Long id = lastOrder.getId();
            mav.addObject("orderID", id);
        }

        return mav;
    }
}
