package com.example.supermarket2.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.example.supermarket2.models.User;
import com.example.supermarket2.repositories.UserRepo;
import com.example.supermarket2.services.AuthService;

@Controller
@RequestMapping("/supermarket")
public class UserController {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private AuthService authservice;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("")
    public ModelAndView getSignup() {
        ModelAndView mav = new ModelAndView("sign-up-form.html");
        User newUser = new User();
        mav.addObject("user", newUser);
        return mav;
    }

    @PostMapping("saveUser")
    public String saveUser(@ModelAttribute User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        this.userRepo.save(user);
        return "redirect:/supermarket/login";

    }

    @GetMapping("login")
    public ModelAndView getLogin() {
        ModelAndView mav = new ModelAndView("login-form.html");
        User newUser = new User();
        mav.addObject("user", newUser);
        return mav;
    }

    @PostMapping("userCheck")
    public String userCheck(@ModelAttribute User user) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                user.getEmail(),
                user.getPassword(),
                user.getAuthorities());
        Authentication authenticated = this.authservice.authenticate(authentication);
        SecurityContextHolder.getContext().setAuthentication(authenticated);
        return "redirect:/supermarket/homepage";
    }
}
