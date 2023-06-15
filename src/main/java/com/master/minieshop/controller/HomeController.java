package com.master.minieshop.controller;

import com.master.minieshop.entity.AppUser;
import com.master.minieshop.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import com.master.minieshop.service.CategoryService;
import com.master.minieshop.service.ImageService;
import com.master.minieshop.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
public class HomeController {
    @Autowired
    private ProductService productService;
    /*
    @Autowired
    private UserService userService;

    private Long k;
    @GetMapping({"home", "/"})
    public String home(Authentication authentication, HttpSession session) {
        if(authentication != null && session.getAttribute("userD") == null){
            AppUser user = userService.findByUsername(authentication.getName());
            session.setAttribute("userD",user);
            k = user.getId();
        }

        else if (authentication != null && session.getAttribute("userD") != null) {
            session.removeAttribute("userD");
            AppUser user = userService.getById(k);
            session.setAttribute("userD",user);
        }
*/
    

    @GetMapping({"home", "/"})
    public String home(Model model) {
        model.addAttribute("products", productService.getAll());
        return "home/index";
    }
    @GetMapping("home/about")
    public String about() {
        return "home/about";
    }
}
