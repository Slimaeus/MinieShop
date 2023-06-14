package com.master.minieshop.controller;

import com.master.minieshop.service.CategoryService;
import com.master.minieshop.service.ImageService;
import com.master.minieshop.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
    private final ProductService productService;

    public HomeController(ProductService productService) {
        this.productService = productService;
    }

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
