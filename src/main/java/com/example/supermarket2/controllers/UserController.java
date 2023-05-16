package com.example.supermarket2.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.example.supermarket2.Dao.*;
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

    @Autowired
    private UserDao userDao;

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
    @GetMapping("reset-pass")
    public ModelAndView getRestpassform() {
        ModelAndView mav = new ModelAndView("reset-pass.html");
        User newUser = new User();
        mav.addObject("user", newUser);
        return mav;
    }

    @PostMapping("change-pass")
    public String changepass(@ModelAttribute User user) {
        String email = user.getEmail();
        User userfinder = this.userRepo.findByEmail(email).orElse(null);
        if (userfinder == null) {
            return "redirect:/supermarket/reset-pass";
        }
        String pass = user.getPassword();
        userfinder.setPassword(bCryptPasswordEncoder.encode(pass));
        this.userRepo.save(userfinder);
        return "redirect:/supermarket/login";

    }
    @PostMapping("/update-password")
    public String updatePassword(@AuthenticationPrincipal User user, @RequestParam String newPassword,
            RedirectAttributes redirectAttributes) {
        Long userID = user.getId();
        boolean success = userDao.updatePassword(userID, newPassword);
        if (success) {
            redirectAttributes.addFlashAttribute("message", "Your password has been changed successfully");
        }
        return "redirect:/supermarket/edit-profile";
    }
}
