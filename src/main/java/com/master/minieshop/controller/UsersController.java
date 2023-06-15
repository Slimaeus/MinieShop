package com.master.minieshop.controller;

import com.master.minieshop.entity.AppUser;
import com.master.minieshop.enumeration.Role;
import com.master.minieshop.service.CustomUserDetailsService;
import com.master.minieshop.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import jakarta.websocket.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.io.Console;
import java.util.List;

@Controller
public class UsersController {
    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String login(Model model, AppUser user) {
        model.addAttribute("userD",user);
        return "users/login";
    }
    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("user", new AppUser());
        return "users/register";
    }
    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("user") AppUser user,
                           BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                model.addAttribute(error.getField() + "_error",
                        error.getDefaultMessage());
            }
            return "users/register";
        }
        user.setRole(Role.LoyalCustomer);
        user.setPassword(new
                BCryptPasswordEncoder().encode(user.getPassword()));
        userService.save(user);
        return "redirect:/login";
    }

    @GetMapping("/user-details/{username}")
    public String userDetail( @PathVariable("username") String username){
        return "users/user-details";
    }

    @GetMapping("/edit-user/{username}")
    public String editUser( @PathVariable("username") String username,HttpSession session, Model model){
        AppUser user = (AppUser)session.getAttribute("userD");
        model.addAttribute("user",user);
        return "users/edit-user";
    }

    @PostMapping("/edit-user/{username}")
    public String editUser(@PathVariable("username") String username, @ModelAttribute("user")@Valid AppUser user
            , HttpSession session, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                model.addAttribute(error.getField() + "_error",
                        error.getDefaultMessage());
            }
            return "users/edit-user";
        }
        else {
            user.setPassword(new
                    BCryptPasswordEncoder().encode(user.getPassword()));
                userService.save(user);
        }
            return "redirect:/login";
    }
}
