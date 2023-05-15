package com.master.minieshop.controller;

import com.master.minieshop.entity.Product;
import com.master.minieshop.service.CategoryService;
import com.master.minieshop.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/{controller}")
public class ProductsController {
    private final ProductService productService;
    private final CategoryService categoryService;

    public ProductsController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @GetMapping({"index", ""})
    public String index(Model model) {
        model.addAttribute("products", productService.getAll());
        return "product/index";
    }

    @GetMapping("/details/{id}")
    public String details(@PathVariable("id") Integer id, Model model) {
        Product product = productService.getById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid product id: " + id));
        model.addAttribute("product", product);
        return "product/details";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("categories", categoryService.getAll());
        return "product/create";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute("product") Product product) {
        productService.save(product);
        return "redirect:/product";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model) {
        Product product = productService.getById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid product id: " + id));
        model.addAttribute("categories", categoryService.getAll());
        model.addAttribute("product", product);
        return "product/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, @ModelAttribute("product") Product product) {
        productService.save(product);
        return "redirect:/product";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id, Model model) {
        Product product = productService.getById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid product id: " + id));
        model.addAttribute("product", product);
        return "product/delete";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id) {
        productService.deleteById(id);
        return "redirect:/product";
    }
}
