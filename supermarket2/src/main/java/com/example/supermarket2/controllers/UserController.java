package com.example.supermarket2.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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

    // for finding a specific user (showing profile page) (can be used by admins)
    @GetMapping("/users/{name}")
    public String getUserByName(@PathVariable String name, Model model) {
        User user = userDao.getUserByName(name);
        model.addAttribute("user", user);
        model.addAttribute("isList", false);
        return "profile.html";
    }

    // showing logged in profile page
    @GetMapping("/profile")
    public String getUserByName(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("isList", false);
        return "profile.html";
    }

    // for editing/updating user info
    @GetMapping("/edit-profile")
    public String editProfile(@AuthenticationPrincipal User user, Model model) {
        if (user != null) {
            model.addAttribute("user", user);
            return "edit-profile";
        } else {
            return "redirect:/";
        }
    }

    @PostMapping("/update-username")
    public String updateUsername(@RequestParam String oldUsername, @RequestParam String newUsername,
            RedirectAttributes redirectAttributes) {
        boolean success = userDao.updateUsername(oldUsername, newUsername);
        if (success) {
            redirectAttributes.addFlashAttribute("message", "Your user name has been changed successfully");
            User user = userDao.getUserByName(newUsername);
            Authentication newAuthentication = new UsernamePasswordAuthenticationToken(
                    user,
                    user.getPassword(),
                    user.getAuthorities());
            SecurityContextHolder.clearContext();
            SecurityContextHolder.getContext().setAuthentication(newAuthentication);
            return "redirect:/supermarket/edit-profile";
        } else {
            return "redirect:/supermarket/edit-profile";
        }
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

    @PostMapping("/update-address")
    public String updateAddress(@AuthenticationPrincipal User user, @RequestParam String addressField,
            RedirectAttributes redirectAttributes) {
        Long userID = user.getId();
        boolean success = userDao.updateAddress(userID, addressField);
        if (success) {
            User updatedUser = userDao.getUserById(userID);
            Authentication newAuthentication = new UsernamePasswordAuthenticationToken(
                    updatedUser,
                    updatedUser.getPassword(),
                    updatedUser.getAuthorities());
            SecurityContextHolder.clearContext();
            SecurityContextHolder.getContext().setAuthentication(newAuthentication);
            updatedUser.setAddress(addressField);
            redirectAttributes.addFlashAttribute("message", "Your address has been updated successfully.");
        } else {
            redirectAttributes.addFlashAttribute("error", "Failed to update your profile picture.");
        }
        return "redirect:/supermarket/edit-profile";
    }
    @PostMapping("/update-CreditCard")
    public String updateCreditCard(@AuthenticationPrincipal User user, @RequestParam long creditCardField,
            RedirectAttributes redirectAttributes) {
        Long userID = user.getId();
        boolean success = userDao.updateCreditCard(userID, creditCardField);
        if (success) {
            User updatedUser = userDao.getUserById(userID);
            Authentication newAuthentication = new UsernamePasswordAuthenticationToken(
                    updatedUser,
                    updatedUser.getPassword(),
                    updatedUser.getAuthorities());
            SecurityContextHolder.clearContext();
            SecurityContextHolder.getContext().setAuthentication(newAuthentication);
            updatedUser.setCreditCard(creditCardField);
            redirectAttributes.addFlashAttribute("message", "Your Credit Card has been updated successfully.");
        } else {
            redirectAttributes.addFlashAttribute("error", "Failed to update your profile picture.");
        }
        return "redirect:/supermarket/edit-profile";
    }
    @PostMapping("/update-Cvv")
    public String updateCvv(@AuthenticationPrincipal User user, @RequestParam int cvvField,
            RedirectAttributes redirectAttributes) {
        Long userID = user.getId();
        boolean success = userDao.updateCvv(userID, cvvField);
        if (success) {
            User updatedUser = userDao.getUserById(userID);
            Authentication newAuthentication = new UsernamePasswordAuthenticationToken(
                    updatedUser,
                    updatedUser.getPassword(),
                    updatedUser.getAuthorities());
            SecurityContextHolder.clearContext();
            SecurityContextHolder.getContext().setAuthentication(newAuthentication);
            updatedUser.setCvv(cvvField);
            redirectAttributes.addFlashAttribute("message", "Your CVV has been updated successfully.");
        } else {
            redirectAttributes.addFlashAttribute("error", "Failed to update your profile picture.");
        }
        return "redirect:/supermarket/edit-profile";
    }
}
