package com.master.minieshop.controller;

import com.master.minieshop.entity.AppUser;
import com.master.minieshop.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
public class HomeController {

    @Autowired
    private UserService userService;
    @GetMapping({"home", "/"})
    public String home(Authentication authentication, HttpSession session) {
        if(authentication != null){
            AppUser user = userService.findByUsername(authentication.getName());
            session.setAttribute("userD",user);
        }
        return "home/index";
    }
    @GetMapping("home/about")
    public String about() {
        return "home/about";
    }
}
