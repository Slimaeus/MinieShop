package com.master.minieshop.controller.admin;

import com.master.minieshop.entity.Category;
import com.master.minieshop.entity.Product;
import com.master.minieshop.entity.Promotion;
import com.master.minieshop.service.CategoryService;
import com.master.minieshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("admin/categories")
public class AdminCategoriesController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping({"index", ""})
    public String showAllCurrentCategory(Model model){
        var currentCategories = categoryService.getAll();
        model.addAttribute("categories",currentCategories);
        return "admin/categories/index";
    }

    @GetMapping("/details/{id}")
    public String details(@PathVariable("id") Integer id, Model model) {
        Category category = categoryService.getById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid category id: " + id));
        model.addAttribute("category", category);
        return "admin/categories/details";
    }

    @GetMapping("/create")
    public String createCategory(Model model){
        model.addAttribute("category",new Category());
        return "admin/categories/create";
    }

    @PostMapping("/create")
    public String createCategory(@ModelAttribute("category") Category category) {
        categoryService.save(category);
        return "redirect:/admin/categories";
    }

    @GetMapping("/edit/{id}")
    public String editCategory(@PathVariable("id") Integer id, Model model) {
        Category category = categoryService.getById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid category id: " + id));
        model.addAttribute("category", category);
        return "admin/categories/edit";
    }

    @PostMapping("/edit/{id}")
    public String editCategory(@PathVariable("id") Integer id, @ModelAttribute("category") Category category) {
        categoryService.save(category);
        return "redirect:/admin/categories";
    }

    @GetMapping("/delete/{id}")
    public String deleteCategory(@PathVariable("id") Integer id, Model model) {
        Category category = categoryService.getById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid category id: " + id));
        model.addAttribute("category", category);
        return "admin/categories/delete";
    }

    @PostMapping("/delete/{id}")
    public String deleteCategory(@PathVariable("id") Integer id) {
        categoryService.deleteById(id);
        return "redirect:/admin/categories";
    }

}
