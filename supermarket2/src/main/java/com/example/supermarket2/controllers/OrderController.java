// package com.example.supermarket2.controllers;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.core.annotation.AuthenticationPrincipal;
// import org.springframework.stereotype.Controller;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.servlet.ModelAndView;
// import org.springframework.web.servlet.mvc.support.RedirectAttributes;

// import com.example.supermarket2.models.Cart;
// import com.example.supermarket2.models.Order;
// import com.example.supermarket2.models.User;
// import com.example.supermarket2.repositories.CartRepo;
// import com.example.supermarket2.repositories.OrderRepo;

// @Controller
// @RequestMapping("/supermarket")
// public class OrderController {

// @Autowired
// private CartRepo cartRepo;

// @Autowired
// private OrderRepo orderRepo;

// @PostMapping("/place-order")
// public String placeOrder(@AuthenticationPrincipal User user) {
// Cart cart = this.cartRepo.findByuserID(user.getId()).orElse(null);
// if (cart != null) {
// Order order = new Order();
// order.setCart(cart);
// order.setSubtotal(cart.getSubtotal());
// order.setUserID(user.getId());
// orderRepo.save(order);
// }
// return "redirect:/supermarket/orderDone";
// }

// @GetMapping("orderDone")
// public ModelAndView orderDone(@AuthenticationPrincipal User user,
// RedirectAttributes redirectAttributes) {
// ModelAndView mav = new ModelAndView("orderDone.html");
// Order order = orderRepo.findByUserID(user.getId()).orElse(null);
// String username = user.getUsername();
// mav.addObject("name", username);
// redirectAttributes.addFlashAttribute("message", "Hello " + username);
// redirectAttributes.addFlashAttribute("message2", "Your order id is " +
// order.getCart().getCartId());
// return mav;
// }
// }
