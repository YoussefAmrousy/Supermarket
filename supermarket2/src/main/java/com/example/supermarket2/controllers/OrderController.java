package com.example.supermarket2.controllers;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.supermarket2.Dto.ProductDto;
import com.example.supermarket2.models.Cart;
import com.example.supermarket2.models.CartItem;
import com.example.supermarket2.models.Order;
import com.example.supermarket2.models.Product;
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

    @Autowired
    private ProductDto productDto;

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
                order.setOrderName(user.getUsername());
                order.setCart(cart);
                order.setSubtotal(cart.getSubtotal());
                order.setUserID(user.getId());
                order.setDateOrdered(currentDate);
                orderRepo.save(order);
                return "redirect:/supermarket/orderDone";

                // Debugging
                // List<CartItem> cartItems = order.getCart().getCartItems();
                // System.out.println("CartItems size: " + cartItems.size());
                // for (CartItem cartItem : cartItems) {
                // System.out.println(cartItem.getProduct().getName());
                // }
            }
        }
        return "redirect:/supermarket/viewCart";
    }

    @GetMapping("orderDone")
    public ModelAndView orderDone(@AuthenticationPrincipal User user) {
        ModelAndView mav = new ModelAndView("orderDone.html");

        String username = user.getUsername();
        mav.addObject("name", username);

        List<Order> userOrders = orderRepo.findAllByuserIDOrderByDateOrderedDesc(user.getId());
        if (!userOrders.isEmpty()) {
            Order lastOrder = userOrders.get(0);
            Long id = lastOrder.getId();
            mav.addObject("orderID", id);
            mav.addObject("address", user.getAddress());
            mav.addObject("subtotal", lastOrder.getSubtotal());
            mav.addObject("date", lastOrder.getDateOrdered());
        }

        return mav;
    }

    @PostMapping("viewOrderDetails")
    public ModelAndView viewDetails(@AuthenticationPrincipal User user,
            @RequestParam(name = "id", required = true) Long id) {
        ModelAndView mav = new ModelAndView("order-details.html");
        Order order = orderRepo.findById(id).orElse(null);
        if (order != null) {
            Long orderID = id;
            List<CartItem> cartItems = order.getCart().getCartItems();
            int subtotal = order.getSubtotal();
            Date date = order.getDateOrdered();
            mav.addObject("orderID", orderID);
            mav.addObject("cartItems", cartItems);
            mav.addObject("subtotal", subtotal);
            mav.addObject("dateOrdered", date);
        }
        return mav;
    }

    @PostMapping("CancelOrder")
    public String cancelOrder(@AuthenticationPrincipal User user,
            @RequestParam(name = "id", required = true) Long id) {
        Order order = orderRepo.findById(id).orElse(null);
        List<CartItem> cartItems = order.getCart().getCartItems();
        for (CartItem cartItem : cartItems) {
            Product product = cartItem.getProduct();
            productDto.increaseProductQuantity(product, cartItem);
        }
        orderRepo.delete(order);
        return "redirect:/supermarket/viewOrders";
    }
}
